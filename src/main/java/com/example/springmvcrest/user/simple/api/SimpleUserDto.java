package com.example.springmvcrest.user.simple.api;

import lombok.Data;

import java.util.List;

@Data
public class SimpleUserDto {
    private Long id;
    private List<Long> interest;
}
