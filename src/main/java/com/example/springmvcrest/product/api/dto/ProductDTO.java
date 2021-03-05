package com.example.springmvcrest.product.api.dto;


import com.example.springmvcrest.product.domain.Attribute;
import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.domain.Tags;
import com.example.springmvcrest.store.api.dto.CustomCategoryDto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private Set<TagsDto>tags = new HashSet<>();
    private List<ProductVariantDto> productVariants = new ArrayList<>();
    private String description;
    private String name;
    private List<ImagesDTO>images = new ArrayList<>();
    private Long customCategory;
    private Set<AttributeDto> attributes = new HashSet<>();
}
