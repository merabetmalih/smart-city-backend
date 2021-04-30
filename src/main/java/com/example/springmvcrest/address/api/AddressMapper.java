package com.example.springmvcrest.address.api;

import com.example.springmvcrest.address.domain.Address;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SimpleUserService.class})
public interface AddressMapper {

    @Mapping(source = "address.user.id", target = "userId")
    AddressDto toDto(Address address);

    @Mapping(source = "addressDto.userId", target = "user")
    Address toModel(AddressDto addressDto);
}
