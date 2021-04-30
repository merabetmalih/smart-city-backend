package com.example.springmvcrest.address.api;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private Integer houseNumber;
    private String street;
    private String city;
    private Long zipCode;
    private String label;
    private Long userId;
}
