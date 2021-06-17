package com.example.springmvcrest.offer.service;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.api.dto.OfferDto;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.offer.domain.OfferState;
import com.example.springmvcrest.offer.domain.OfferType;
import com.example.springmvcrest.offer.repository.OfferRepository;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.domain.ProductVariant;
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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.example.springmvcrest.offer.domain.OfferType.FIXED;
import static com.example.springmvcrest.offer.domain.OfferType.PERCENTAGE;

@Service
@AllArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final ProductMapper productMapper;

    @Transactional
    public Response<String> createOffer(OfferCreationDto offerCreationDto){
        if(!DateUtil.isValidDate(offerCreationDto.getStartDate()) || !DateUtil.isValidDate(offerCreationDto.getEndDate())){
            throw new DateException("error.date.invalid");
        }
        offerCreationDto.setId(null);
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offer-> SetOffer.apply(offer).apply(GetOfferTypes.get()))
                .map(offerRepository::save);
        return new Response<>("created.");
    }

    @Transactional
    public Response<String> deleteOffer(Long id){
        Offer offer=offerRepository.findById(id)
                .orElseThrow(() -> new OfferException("error.offer.notFound"));
        offerRepository.delete(offer);
        return new Response<>("deleted.");
    }

    public Response<String> updateOffer(OfferCreationDto offerCreationDto){
        if(!DateUtil.isValidDate(offerCreationDto.getStartDate()) || !DateUtil.isValidDate(offerCreationDto.getEndDate())){
            throw new DateException("error.date.invalid");
        }
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offer-> SetOffer.apply(offer).apply(GetOfferTypes.get()))
                .map(offerRepository::save);
        return new Response<>("updated.");
    }

    public List<OfferDto> getOffersByProviderId(Long id){
        return offerRepository.findByStore_Provider_Id(id).stream()
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
                .peek(key -> key.setProductVariants(map.get(key)))
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }
}
