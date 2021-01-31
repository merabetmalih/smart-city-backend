package com.example.springmvcrest.store.controller;

import com.example.springmvcrest.store.api.StoreDto;
import com.example.springmvcrest.store.service.CategoryStoreService;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.utils.Results;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;
    private final CategoryStoreService categoryStoreService;

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public StoreDto createStore(@RequestBody StoreDto storeDto) {
        return storeService.create(storeDto);
    }

    @GetMapping("/category/all")
    @ResponseStatus(value = HttpStatus.OK)
    public Results<String> getAllCategoryStore(){
        return new Results<>(categoryStoreService.findAll());
    }
}
