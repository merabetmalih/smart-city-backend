package com.example.springmvcrest.store.api.dto;

import lombok.Data;

@Data
public class StoreInformationDto {
    private Long providerId;
    private String address;
    private String telephoneNumber;
    private String defaultTelephoneNumber;
}
