package com.example.springmvcrest.user.api.mapper;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.provider.domain.Provider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    UserDto ToDto(Provider provider);
    Provider toModel(UserDto userDto);
}
