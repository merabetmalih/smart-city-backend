package com.example.springmvcrest.product.api.mapper;

import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "category.subCategorys", target = "subCategorys", qualifiedByName = "subCategory")
    CategoryDto toDto(Category category);
    @Named("subCategory")
    default Set<String> subCategory( Set<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }


  //  Category toModel(CategoryDto categoryDto);
}
