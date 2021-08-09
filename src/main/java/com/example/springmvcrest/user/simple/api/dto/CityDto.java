package com.example.springmvcrest.user.simple.api.dto;

import lombok.Data;

@Data
public class CityDto {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String displayName;
    private String country;
    private Long userId;
}
