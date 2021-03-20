package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.user.simple.api.dto.CartProductVariantDto;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface CartProductVariantMapper {

    @Mapping(source = "cartProductVariant.cartProductVariant", target = "productVariantDto")
    CartProductVariantDto toDto(CartProductVariant cartProductVariant);

    @Mapping(source = "cartProductVariantDto.productVariantDto", target = "cartProductVariant")
    CartProductVariant toModel(CartProductVariantDto cartProductVariantDto);
}
