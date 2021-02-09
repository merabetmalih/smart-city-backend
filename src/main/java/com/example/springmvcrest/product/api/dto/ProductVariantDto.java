package com.example.springmvcrest.product.api.dto;

import com.example.springmvcrest.product.domain.AttributeValue;
import lombok.Data;

@Data
public class ProductVariantDto {
    private Long id;
    AttributeValue attributeValue;
    private Double price;
    private Integer unit;
    private String image;
}
