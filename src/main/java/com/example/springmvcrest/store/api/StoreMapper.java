package com.example.springmvcrest.store.api;

import com.example.springmvcrest.store.domain.CategoryStore;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.service.CategoryStoreService;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { ProviderService.class ,CategoryStoreService.class})
public interface StoreMapper {


    @Mapping(source = "store.provider.id", target = "provider")
    @Mapping(source = "store.categories", target = "categories")
    StoreDto ToDto(Store store);
    default Set<String> mapCategoryStore(Set<CategoryStore> categories ){
        return categories
                .stream()
                .map(CategoryStore::getName)
                .collect(Collectors.toSet());
    }

    Store ToModel(StoreDto storeDto);

}
