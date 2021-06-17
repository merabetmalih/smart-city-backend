package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.offer.api.mapper.OfferMapper;
import com.example.springmvcrest.product.api.dto.ProductVariantDto;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.service.ProductVariantService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" ,uses = {ProductVariantService.class, OfferMapper.class,ProductMapper.class})
public interface ProductVariantMapper {

    ProductVariant toModel(ProductVariantDto productVariantDto);

    @Mapping(source = "productVariant.offers", target = "offer", qualifiedByName = "getVariantOffer")
    ProductVariantDto toDto(ProductVariant productVariant);//
}
