package com.example.springmvcrest.offer.api.mapper;

import com.example.springmvcrest.offer.api.dto.OfferCreationDto;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.product.service.ProductVariantService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.utils.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoreService.class, ProductVariantService.class, DateUtil.class})
public interface OfferMapper {

    @Mapping(source = "offerCreationDto.providerId", target = "store", qualifiedByName = "findStoreByProviderId")
    @Mapping(source = "offerCreationDto.startDate", target = "startDate", qualifiedByName = "parseDateTime")
    @Mapping(source = "offerCreationDto.endDate", target = "endDate", qualifiedByName = "parseDateTime")
    Offer toModel(OfferCreationDto offerCreationDto);
}
