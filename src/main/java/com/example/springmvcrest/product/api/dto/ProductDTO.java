package com.example.springmvcrest.product.api.dto;


import com.example.springmvcrest.store.api.dto.StoreAddressDto;
import lombok.Data;

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
    private StoreAddressDto storeAddress;
    private String storeName;
    private Long storeId;
}
