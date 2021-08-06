package com.example.springmvcrest.nominatim;

import lombok.Data;

import java.io.Serializable;

@Data
public class NominatimResponse implements Serializable {
    private Double lat;
    private Double lon;
    private String display_name;
    private Double importance;
}
