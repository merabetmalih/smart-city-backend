package com.example.springmvcrest.store.api;

import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.user.provider.domain.Provider;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class StoreDto {
    private String name;
    private String description;
    private String address;
    private Set<String> defaultCategories= new HashSet<>();
    private Long provider;
    private String imageStore;
}
