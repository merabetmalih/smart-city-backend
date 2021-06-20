package com.example.springmvcrest.product.service;

import com.example.springmvcrest.offer.api.dto.OfferVariantDto;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.repository.ProductVariantRepository;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final OfferMapper offerMapper;

    public ProductVariant findById(Long id)
    {
        return productVariantRepository.findById(id)
                .orElseThrow(MultipleStoreException::new);
    }

    public Offer getVariantOffer(Set<Offer> offers){
        Date todayDate=new Date();
        if (offers!=null){
            return offers.stream()
                    .filter(offer -> todayDate.after(offer.getStartDate()) && todayDate.before(offer.getEndDate()))
                    .min(Comparator.comparing(Offer::getStartDate, Comparator.naturalOrder()))
                    .orElse(null);
        }
        return null;
    }

    @Named("getVariantOffer")
    public OfferVariantDto getVariantOfferDto(Set<Offer> offers){
        Date todayDate=new Date();
        if (offers!=null){
            return offers.stream()
                    .filter(offer -> !offer.getDeleted())
                    .filter(offer -> todayDate.after(offer.getStartDate()) && todayDate.before(offer.getEndDate()))
                    .min(Comparator.comparing(Offer::getStartDate, Comparator.naturalOrder()))
                    .map(offerMapper::toDtoVariant)
                    .orElse(null);
        }
        return null;
    }
}
