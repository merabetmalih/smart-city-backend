package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.product.api.dto.ImagesDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.api.mapper.ProductVariantMapper;
import com.example.springmvcrest.product.domain.Images;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.user.simple.api.dto.CartProductVariantDto;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = {ProductMapper.class, ProductVariantMapper.class})
public interface CartProductVariantMapper {

    @Mapping(source = "cartProductVariant.cartProductVariant", target = "productVariant")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "productImage", qualifiedByName = "getMainProductImage")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "productName", qualifiedByName = "getMainProductName")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "storeName", qualifiedByName = "getStoreName")
    @Mapping(source = "cartProductVariant.cartProductVariant.product", target = "storeId", qualifiedByName = "getStoreId")
    CartProductVariantDto toDto(CartProductVariant cartProductVariant);

    @Named("getMainProductImage")
    default ImagesDTO getMainProductImage(Product product) { return  new ImagesDTO(product.getImages().get(0).getImage()); }
    @Named("getMainProductName")
    default String getMainProductName(Product product) {
        return  product.getName();
    }
    @Named("getStoreName")
    default String getStoreName(Product product) {
        return product.getCustomCategory().getStore().getName();
    }
    @Named("getStoreId")
    default Long getStoreId(Product product) {
        return product.getCustomCategory().getStore().getId();
    }


    @Mapping(source = "cartProductVariantDto.productVariant", target = "cartProductVariant")
    CartProductVariant toModel(CartProductVariantDto cartProductVariantDto);
}
