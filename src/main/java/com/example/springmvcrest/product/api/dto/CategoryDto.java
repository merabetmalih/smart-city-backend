package com.example.springmvcrest.product.api.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Set<String> subCategorys;
}
