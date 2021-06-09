package com.example.springmvcrest.offer.service;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.offer.repository.OfferRepository;
import com.example.springmvcrest.utils.Errorhandler.OfferException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    @Transactional
    public Response<String> createOffer(OfferCreationDto offerCreationDto){
        offerCreationDto.setId(null);
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
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
        Optional.of(offerCreationDto)
                .map(offerMapper::toModel)
                .map(offerRepository::save);
        return new Response<>("updated.");
    }
}
