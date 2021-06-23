package com.example.springmvcrest.store.api.mapper;

import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.store.api.dto.StoreInformationDto;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProviderService.class, CategoryService.class })

public interface StoreInformationMapper {

    @Mapping(source = "store.defaultCategories", target = "defaultCategoriesList",qualifiedByName = "getCategoriesList")
    StoreInformationDto ToDto(Store store);

}
