package com.example.springmvcrest.domain.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private String email;
    private String userName;
    private String passWord1;
    private String passWord2;
    private Set<String> categories=new HashSet<>();

}
