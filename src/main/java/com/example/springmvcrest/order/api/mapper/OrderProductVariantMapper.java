package com.example.springmvcrest.order.api.mapper;

import com.example.springmvcrest.order.api.dto.OrderProductVariantDto;
import com.example.springmvcrest.order.domain.OrderProductVariant;
import com.example.springmvcrest.product.api.dto.ImagesDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.api.mapper.ProductVariantMapper;
import com.example.springmvcrest.product.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses = {ProductVariantMapper.class})
public interface OrderProductVariantMapper {

    @Mapping(source = "orderProductVariant.orderProductVariant", target = "productVariant",qualifiedByName = "toDtoOrder")
    @Mapping(source = "orderProductVariant.orderProductVariant.product", target = "productImage", qualifiedByName = "getParentProductImage")
    @Mapping(source = "orderProductVariant.orderProductVariant.product", target = "productName", qualifiedByName = "getParentProductName")
    OrderProductVariantDto toDto(OrderProductVariant orderProductVariant);

    @Named("getParentProductImage")
    default ImagesDTO getParentProductImage(Product product) { return  new ImagesDTO(product.getImages().get(0).getImage()); }
    @Named("getParentProductName")
    default String getParentProductName(Product product) {
        return  product.getName();
    }




    @Mapping(source = "orderProductVariantDto.productVariant", target = "orderProductVariant")
    OrderProductVariant toModel(OrderProductVariantDto orderProductVariantDto);
}
