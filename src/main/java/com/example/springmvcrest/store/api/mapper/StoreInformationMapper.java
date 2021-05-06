package com.example.springmvcrest.store.api.mapper;

import com.example.springmvcrest.store.api.dto.StoreInformationDto;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.service.DefaultCategoryService;
import com.example.springmvcrest.user.provider.service.ProviderService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ProviderService.class , DefaultCategoryService.class})

public interface StoreInformationMapper {

    StoreInformationDto ToDto(Store store);
}
