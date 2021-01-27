package com.example.springmvcrest.user.api.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String userName;
    private String passWord;
    private String passWord2;
}
