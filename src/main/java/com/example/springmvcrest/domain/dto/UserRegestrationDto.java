package com.example.springmvcrest.domain.dto;

import lombok.Data;

@Data
public class UserRegestrationDto {
    private Long id;
    private String email;
    private String userName;
    private String response;
    private String token;

    public UserRegestrationDto(Long id, String email, String userName, String response,String token) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.response = response;
        this.token=token;
    }
}
