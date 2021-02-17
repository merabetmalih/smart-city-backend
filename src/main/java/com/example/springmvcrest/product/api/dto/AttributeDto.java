package com.example.springmvcrest.product.api.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AttributeDto {
    private String name;
    private Set<AttributeValueDto> attributeValues = new HashSet<>();
}
