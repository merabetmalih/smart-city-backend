package com.example.springmvcrest.store.api.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class StoreCreationDto {
    private String name;
    private String description;
    private StoreAddressDto storeAddress;
    private Long provider;
    private String imageStore;
    private String telephoneNumber;
    private String defaultTelephoneNumber;
}
