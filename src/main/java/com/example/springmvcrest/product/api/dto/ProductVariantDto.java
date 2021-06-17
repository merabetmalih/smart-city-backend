package com.example.springmvcrest.product.api.dto;

import com.example.springmvcrest.offer.api.dto.OfferVariantDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductVariantDto {
    private Long id;
    List<ProductVariantAttributeValueDto> productVariantAttributeValuesProductVariant=new ArrayList<>();
    private Double price;
    private Integer unit;
    private String image;
    private OfferVariantDto offer;
}
