package com.example.springmvcrest.store.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoreInformationCreationDto {
    private Long providerId;
    private String address;
    private String telephoneNumber;
    private String defaultTelephoneNumber;
    private List<String> defaultCategories;
}
