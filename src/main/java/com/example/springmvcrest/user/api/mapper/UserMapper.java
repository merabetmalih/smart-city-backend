package com.example.springmvcrest.user.api.mapper;

import com.example.springmvcrest.user.domain.User;
import com.example.springmvcrest.user.api.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper  {
    UserDto ToDto(User user);

    User toModel(UserDto userDto);
}
