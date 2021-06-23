package com.example.springmvcrest.product.controller;


import com.example.springmvcrest.product.api.dto.CategoryDto;
import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Results<CategoryDto> getAllCategories() {
        return  new Results<>(categoryService.getAllCategory());
    }
}
