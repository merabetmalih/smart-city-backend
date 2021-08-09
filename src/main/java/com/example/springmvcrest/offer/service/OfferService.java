package com.example.springmvcrest.offer.service;

import com.example.springmvcrest.notification.domain.Notification;
import com.example.springmvcrest.notification.domain.NotificationType;
import com.example.springmvcrest.notification.service.NotificationService;
import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.api.dto.OfferDto;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.offer.domain.OfferState;
import com.example.springmvcrest.offer.domain.OfferType;
import com.example.springmvcrest.offer.repository.OfferRepository;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.OfferException;
import com.example.springmvcrest.utils.Response;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.springmvcrest.offer.domain.OfferType.FIXED;
import static com.example.springmvcrest.offer.domain.OfferType.PERCENTAGE;
import static com.example.springmvcrest.utils.DateUtil.parseStringSimpleFormat;

@Service
@AllArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final ProductMapper productMapper;
    private final NotificationService notificationService;
    private final SimpleUserService simpleUserService;

    @Transactional
    public Response<String> createOffer(OfferCreationDto offerCreationDto){
        if(!DateUtil.isValidDate(offerCreationDto.getStartDate()) || !DateUtil.isValidDate(offerCreationDto.getEndDate())){
            throw new DateException("error.date.invalid");
        }
        offerCreationDto.setId(null);
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offer-> SetOffer.apply(offer).apply(GetOfferTypes.get()))
                .map(offerRepository::save)
                .map(this::prepareNotification)
                .map(this::setOffersUser);
        return new Response<>("created.");
    }

    private Offer setOffersUser(Offer offer){
        simpleUserService.findSimpleUserByInterestCenterAndAround(offer.getStore())
                .forEach(user -> simpleUserService.setOffers(user,offer));
        return offer;
    }

    private Offer prepareNotification(Offer offer){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        offer.getStore().getDefaultCategories()
                .forEach(category -> threadPool.submit(() -> sendNotification(category,offer)));
        threadPool.shutdown();
        return offer;
    }

    private void sendNotification(Category category, Offer offer){
        switch (offer.getType()){
            case FIXED:
                notificationService.sendNotification(
                        Notification.builder()
                                .title("New discount")
                                .message("Check "+"-"+offer.getNewPrice()+"$"+" off at "+offer.getStore().getName()+", start on "+parseStringSimpleFormat(offer.getStartDate()))
                                .type(NotificationType.DISCOUNT)
                                .topic(category.getName())
                                .build()
                );
                break;

            case PERCENTAGE:
                notificationService.sendNotification(
                        Notification.builder()
                                .title("New discount")
                                .message("Check "+"-"+offer.getPercentage()+"%"+" off at "+offer.getStore().getName()+", start on "+parseStringSimpleFormat(offer.getStartDate()))
                                .type(NotificationType.DISCOUNT)
                                .topic(category.getName())
                                .build()
                );
                break;
        }
    }

    @Transactional
    public Response<String> deleteOffer(Long id){
        Offer offer=offerRepository.findById(id)
                .orElseThrow(() -> new OfferException("error.offer.notFound"));
        offer.setDeleted(true);
        offerRepository.save(offer);
        return new Response<>("deleted.");
    }

    public Response<String> updateOffer(OfferCreationDto offerCreationDto){
        if(!DateUtil.isValidDate(offerCreationDto.getStartDate()) || !DateUtil.isValidDate(offerCreationDto.getEndDate())){
            throw new DateException("error.date.invalid");
        }
        Offer oldOffer=offerRepository.findById(offerCreationDto.getId())
                .orElseThrow(() -> new OfferException("error.offer.notFound"));
        deleteOffer(oldOffer.getId());

        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offer -> setOfferParent(offer,oldOffer))
                .map(offer-> SetOffer.apply(offer).apply(GetOfferTypes.get()))
                .map(offerRepository::save)
                .map(this::setOffersUser);
        return new Response<>("updated.");
    }

    private Offer setOfferParent(Offer offer,Offer oldOffer){
        offer.setParentOffer(oldOffer);
        offer.setId(null);
        return offer;
    }

    public List<OfferDto> getOffersByProviderId(Long id){
        return offerRepository.findByStore_Provider_Id(id).stream()
                .filter(offer -> !offer.getDeleted())
                .map(offerMapper::toDto)
                .collect(Collectors.toList());
    }

    private static Function<Offer, Offer> SetOfferFixed= offer -> {
        offer.setPercentage(0);
        return offer;
    };

    private static Function<Offer, Offer> SetOfferPercentage= offer -> {
        offer.setNewPrice(0.0);
        return offer;
    };

    private static Supplier<List<Pair<OfferType,Function<Offer, Offer>>>> GetOfferTypes= ()->{
        List<Pair<OfferType,Function<Offer, Offer>>> types=new ArrayList<>();
        types.add(new Pair<>(PERCENTAGE,SetOfferPercentage));
        types.add(new Pair<>(FIXED,SetOfferFixed));
        return types;
    };

    private static Function<Offer,Function<List<Pair<OfferType,Function<Offer, Offer>>>,//two inputs
            //output
            Offer>> SetOffer =
            offer ->(allTypes ->{
        return allTypes.stream()
                .filter(type -> type.getKey().equals(offer.getType()))
                .findFirst()
                .map(Pair::getValue)
                .map(func -> func.apply(offer))
                .orElseThrow(()-> new OfferException("error.offer.type.notFound"));
    });

    @Named("setOfferState")
    public OfferState setOfferState(Offer offer){
        Date date=new Date();
        if(date.before(offer.getStartDate())){
            return OfferState.PLANNED;
        }
        if(date.after(offer.getStartDate()) && date.before(offer.getEndDate())){
            return OfferState.ACTIVE;
        }else {
            return OfferState.EXPIRED;
        }
    }

    @Named("getProductList")
    public List<ProductDTO> getProductList(Set<ProductVariant> productVariants) {
        Map<Product,List<ProductVariant>> map=new HashMap<>();
        for (ProductVariant productVariant :productVariants) {
            Product product=productVariant.getProduct();
            if(map.containsKey(product)){
                map.get(product).add(productVariant);
            }else {
                map.put(product,new ArrayList<>(Collections.singletonList(productVariant)));
            }
        }
        return map.keySet().stream()
                .filter(product -> !product.getDeleted())
                .peek(key -> key.setProductVariants(map.get(key)))
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchOfferByPosition(Double lat,Double lon){
        Date todayDate=new Date();
        return offerRepository.findAll().stream()
                .filter(offer -> !offer.getDeleted())
                .filter(offer -> todayDate.after(offer.getStartDate()) && todayDate.before(offer.getEndDate()))
                .filter(offer -> isAround(lat,lon,offer.getStore()))
                .map(offerMapper::toDto)
                .flatMap(offerDto -> offerDto.getProducts().stream())
                .collect(Collectors.toList());
    }

    private Boolean isAround(Double latitude, Double longitude, Store store){
        double distance=12.0;
        return distFrom(
                latitude,
                longitude,
                store.getStoreAddress().getLatitude(),
                store.getStoreAddress().getLongitude()) < distance;
    }

    private Double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return  (earthRadius * c)/1000;
    }
}
