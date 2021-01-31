package com.example.springmvcrest.store.service;


import com.example.springmvcrest.store.domain.CategoryStore;
import com.example.springmvcrest.store.repository.CategoryStoreRepository;
import com.example.springmvcrest.store.service.exception.CategoryStoreNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryStoreService {
    private final CategoryStoreRepository categoryStoreRepository;


    public CategoryStore findByName(String name){
        return categoryStoreRepository.findByName(name)
                .orElseThrow(CategoryStoreNotFoundException::new);
    }

    public Set<String> findAll(){
        return categoryStoreRepository.findAll()
                .stream()
                .map(CategoryStore::getName)
                .collect(Collectors.toSet());
    }
}
