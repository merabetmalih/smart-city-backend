package com.example.springmvcrest.flashDeal.api.mapper;

import com.example.springmvcrest.flashDeal.api.dto.FlashDealCreationDto;
import com.example.springmvcrest.flashDeal.api.dto.FlashDealDto;
import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.store.service.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoreService.class})
public interface FlashDealMapper {

    @Mapping(source = "flashDealCreationDto.providerId", target = "store", qualifiedByName = "findStoreByProviderId")
    FlashDeal toModel(FlashDealCreationDto flashDealCreationDto);


    @Mapping(source = "flashDeal.store", target = "storeName", qualifiedByName = "getStoreName")
    @Mapping(source = "flashDeal.store", target = "storeAddress", qualifiedByName = "getStoreAddress")
    @Mapping(source = "flashDeal.store", target = "latitude", qualifiedByName = "getStoreAddressLat")
    @Mapping(source = "flashDeal.store", target = "longitude", qualifiedByName = "getStoreAddressLon")
    FlashDealDto toDto(FlashDeal flashDeal);
}
