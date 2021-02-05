package com.example.springmvcrest.store.api;

import com.example.springmvcrest.store.domain.CustomCategory;
import com.example.springmvcrest.store.service.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoreService.class })
public interface CustomCategoryMapper {
    @Mapping(source = "customCategory.store.provider.id", target = "provider")
    CustomCategoryDto ToDto(CustomCategory customCategory);

    @Mapping(source = "customCategoryDto.provider", target = "store")
    CustomCategory ToModel(CustomCategoryDto customCategoryDto);
}
