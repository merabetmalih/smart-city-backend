package com.example.springmvcrest.politics.api.mapper;

import com.example.springmvcrest.politics.api.dto.PoliticsDto;
import com.example.springmvcrest.politics.domain.Politics;
import com.example.springmvcrest.store.service.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoreService.class })

public interface PoliticsMapper {

    PoliticsDto toDto(Politics politics);

    @Mapping(source = "politicsDto.providerId", target = "store")
    Politics toModel(PoliticsDto politicsDto);

}
