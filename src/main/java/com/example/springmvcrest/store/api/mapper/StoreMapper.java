package com.example.springmvcrest.store.api.mapper;

import com.example.springmvcrest.store.api.dto.StoreDto;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProviderService.class })
public interface StoreMapper {

    @Mapping(source = "store.provider.id", target = "provider")
    StoreDto ToDto(Store store);

    Store ToModel(StoreDto storeDto);
}
