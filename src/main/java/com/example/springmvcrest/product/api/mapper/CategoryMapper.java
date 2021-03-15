package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toModel(CategoryDto categoryDto);
}
