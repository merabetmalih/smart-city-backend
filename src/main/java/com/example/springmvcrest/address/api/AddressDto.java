package com.example.springmvcrest.address.api;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
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
    private String apartmentNumber;
    private String businessName;
    private String doorCodeName;
    private Long userId;
}
