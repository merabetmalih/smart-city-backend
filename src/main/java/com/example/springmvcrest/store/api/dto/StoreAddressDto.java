package com.example.springmvcrest.store.api.dto;
import lombok.Data;

@Data
public class StoreAddressDto {
    private String streetNumber;
    private String admin;
    private String subAdmin;
    private String locality;
    private String streetName;
    private String postalCode;
    private String countryCode;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private String fullAddress;
}