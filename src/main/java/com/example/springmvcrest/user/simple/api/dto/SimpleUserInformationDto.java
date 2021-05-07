package com.example.springmvcrest.user.simple.api.dto;

import lombok.Data;

import java.util.Date;
@Data
public class SimpleUserInformationDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String birthDay;
}
