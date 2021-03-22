package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.product.api.dto.ImagesDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.Images;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.user.simple.api.dto.CartProductVariantDto;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface CartProductVariantMapper {

    @Mapping(source = "cartProductVariant.cartProductVariant", target = "productVariant")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "productImage", qualifiedByName = "getMainProductImage")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "productName", qualifiedByName = "getMainProductName")
    CartProductVariantDto toDto(CartProductVariant cartProductVariant);
    @Named("getMainProductImage")
    default ImagesDTO getMainProductImage(Product product) {
        return  new ImagesDTO(
                product.getImages().get(0).getImage());
    }
    @Named("getMainProductName")
    default String getMainProductName(Product product) {
        return  product.getName();
    }

    @Mapping(source = "cartProductVariantDto.productVariant", target = "cartProductVariant")
    CartProductVariant toModel(CartProductVariantDto cartProductVariantDto);
}
