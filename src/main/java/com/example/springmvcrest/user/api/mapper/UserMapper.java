package com.example.springmvcrest.user.api.mapper;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto ToDto(SimpleUser simpleUser);

    SimpleUser toModel(UserDto userDto);

}
