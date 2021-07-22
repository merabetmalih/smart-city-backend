package com.example.springmvcrest.nominatim;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private Double lat;
    private Double lon;
    private String city;
    private Double importance;
}
