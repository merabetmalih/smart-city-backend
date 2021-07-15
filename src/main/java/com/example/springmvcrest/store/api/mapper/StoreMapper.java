package com.example.springmvcrest.store.api.mapper;

import com.example.springmvcrest.product.api.mapper.CategoryMapper;
import com.example.springmvcrest.store.api.dto.StoreCreationDto;
import com.example.springmvcrest.store.api.dto.StoreDto;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProviderService.class ,StoreAddressMapper.class, CategoryMapper.class})
public interface StoreMapper {

    @Mapping(source = "store.provider.id", target = "provider")
    StoreCreationDto ToDto(Store store);

    StoreDto toDto(Store store);

    Store ToModel(StoreCreationDto storeCreationDto);
}
