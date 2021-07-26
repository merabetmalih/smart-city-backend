package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.user.simple.api.dto.CityDto;
import com.example.springmvcrest.user.simple.domain.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDto toDto(City city);

    City toModel(CityDto cityDto);
}
