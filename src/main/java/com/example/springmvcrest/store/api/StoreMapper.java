package com.example.springmvcrest.store.api;

import com.example.springmvcrest.store.domain.DefaultCategory;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.service.DefaultCategoryService;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { ProviderService.class , DefaultCategoryService.class})
public interface StoreMapper {


    @Mapping(source = "store.provider.id", target = "provider")
    @Mapping(source = "store.defaultCategories", target = "defaultCategories")
    StoreDto ToDto(Store store);
    default Set<String> mapCategoryStore(Set<DefaultCategory> categories ){
        return categories
                .stream()
                .map(DefaultCategory::getName)
                .collect(Collectors.toSet());
    }

    Store ToModel(StoreDto storeDto);

}
