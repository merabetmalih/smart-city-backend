package com.example.springmvcrest.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Results {
    private List<ProductDTO> result;

}
