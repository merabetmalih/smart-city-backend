package com.example.springmvcrest.nominatim;

import lombok.Data;

@Data
public class NominatimCityNameResponse {
    private String name;
    private AddressResponse address;
}
