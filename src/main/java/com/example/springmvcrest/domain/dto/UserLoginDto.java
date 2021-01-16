package com.example.springmvcrest.domain.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private Long id;
    private String email;
    private String response;

    public UserLoginDto(Long id, String email, String response) {
        this.id = id;
        this.email = email;
        this.response = response;
    }
}
