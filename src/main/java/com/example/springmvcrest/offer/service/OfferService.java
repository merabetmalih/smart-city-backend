package com.example.springmvcrest.offer.service;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.offer.domain.OfferType;
import com.example.springmvcrest.offer.repository.OfferRepository;
import com.example.springmvcrest.utils.DateUtil;
import com.example.springmvcrest.utils.Errorhandler.DateException;
import com.example.springmvcrest.utils.Errorhandler.OfferException;
import com.example.springmvcrest.utils.Response;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.example.springmvcrest.offer.domain.OfferType.FIXED;
import static com.example.springmvcrest.offer.domain.OfferType.PERCENTAGE;

@Service
@AllArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

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
            Offer>> SetOffer = offer ->(allTypes ->{

        return allTypes.stream()
                .filter(type -> type.getKey().equals(offer.getType()))
                .findFirst()
                .map(Pair::getValue)
                .map(func -> func.apply(offer))
                .orElseThrow(()-> new OfferException("error.offer.type.notFound"));
    });


    @Transactional
    public Response<String> deleteOffer(Long id){
        Offer offer=offerRepository.findById(id)
                .orElseThrow(() -> new OfferException("error.offer.notFound"));
        offerRepository.delete(offer);
        return new Response<>("deleted.");
    }

    public Response<String> updateOffer(OfferCreationDto offerCreationDto){
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offerRepository::save);
        return new Response<>("updated.");
    }
}
