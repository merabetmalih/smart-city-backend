package com.example.springmvcrest.store.api.mapper;

import com.example.springmvcrest.store.api.dto.StoreAddressDto;
import com.example.springmvcrest.store.domain.StoreAddress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreAddressMapper {
    StoreAddressDto ToDto(StoreAddress storeAddress);
}
