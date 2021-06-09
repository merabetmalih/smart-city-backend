package com.example.springmvcrest.offer.api.mapper;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.product.service.ProductVariantService;
import com.example.springmvcrest.store.service.StoreService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StoreService.class, ProductVariantService.class})
public interface OfferMapper {

    Offer toModel(OfferCreationDto offerCreationDto);
}
