package com.example.springmvcrest.user.controller;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class Results {
    private List<ProductDTO> result;

    public Results(List<ProductDTO> result) {
        this.result = result;
    }
}
