package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.user.simple.api.dto.SimpleUserInformationDto;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.utils.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;


@Mapper(componentModel = "spring")
public interface SimpleUserInformationMapper {
    @Mapping(source = "simpleUser.birthDay", target = "birthDay", qualifiedByName = "getStringDate")
    SimpleUserInformationDto ToDto(SimpleUser simpleUser);
    @Named("getStringDate")
    default String getStringDate(Date date) {
        if(date!=null){
            return DateUtil.parseString(date);
        }
        return null;
    }
}
