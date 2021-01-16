package com.example.springmvcrest.api.model;

import com.example.springmvcrest.domain.Category;
import com.example.springmvcrest.domain.Tags;
import lombok.Data;

import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private Set<Category> categories;
    private Set<Tags> tags;
    private String description;
    private String name;
    private Double price;
    private Integer unit;
    private String color;
    private String origin;
    private String image;
}
