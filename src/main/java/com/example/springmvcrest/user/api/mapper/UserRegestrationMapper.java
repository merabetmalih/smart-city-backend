package com.example.springmvcrest.user.api.mapper;

import com.example.springmvcrest.user.api.dto.UserDto;
import com.example.springmvcrest.user.api.dto.UserRegestrationDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserRegestrationMapper {

    UserRegestrationDto ToRegestrationDto(UserDto userDto);
    @AfterMapping
     static UserRegestrationDto update(@MappingTarget UserRegestrationDto userRegestrationDto){
        userRegestrationDto.setResponse("successfully registered");
        return userRegestrationDto;
    }
}
