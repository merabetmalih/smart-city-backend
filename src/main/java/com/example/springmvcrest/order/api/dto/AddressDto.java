package com.example.springmvcrest.order.api.dto;

import lombok.Data;

@Data
public class AddressDto {
    Integer houseNumber;
    String street;
    String city;
    Long zipCode;
}
