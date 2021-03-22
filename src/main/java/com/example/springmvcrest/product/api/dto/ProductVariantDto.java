package com.example.springmvcrest.product.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductVariantDto {
    private Long id;
    List<ProductVariantAttributeValueDto> productVariantAttributeValuesProductVariant=new ArrayList<>();
    private Double price;
    private Integer unit;
    private String image;
}
