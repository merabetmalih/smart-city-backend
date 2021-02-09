package com.example.springmvcrest.product.api.dto;


import com.example.springmvcrest.product.domain.Attribute;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.domain.Tags;
import com.example.springmvcrest.store.api.dto.CustomCategoryDto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private Set<TagsDto>tags = new HashSet<>();
    private Set<ProductVariant>productVariants = new HashSet<>();
    private String description;
    private String name;
    private Set<ImagesDTO>images = new HashSet<>();
    private Long customCategoryDto;
}
