package com.example.springmvcrest.store.service;


import com.example.springmvcrest.store.domain.DefaultCategory;
import com.example.springmvcrest.store.repository.DefaultCategoryRepository;
import com.example.springmvcrest.store.service.exception.DefaultCategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultCategoryService {
    private final DefaultCategoryRepository defaultCategoryRepository;


    public DefaultCategory findByName(String name){
        return defaultCategoryRepository.findByName(name)
                .orElseThrow(DefaultCategoryNotFoundException::new);
    }

    public Set<String> findAll(){
        return defaultCategoryRepository.findAll()
                .stream()
                .map(DefaultCategory::getName)
                .collect(Collectors.toSet());
    }
}
