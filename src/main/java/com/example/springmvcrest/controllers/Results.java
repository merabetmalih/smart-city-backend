package com.example.springmvcrest.controllers;

import com.example.springmvcrest.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class Results {
    private List<Product> result;

    public Results(List<Product> result) {
        this.result = result;
    }
}
