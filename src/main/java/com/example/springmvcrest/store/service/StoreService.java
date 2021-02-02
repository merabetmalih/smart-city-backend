package com.example.springmvcrest.store.service;

import com.example.springmvcrest.store.api.StoreDto;
import com.example.springmvcrest.store.api.StoreMapper;
import com.example.springmvcrest.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;


    public StoreDto create(StoreDto storeDto) {
        return Optional.of(storeDto)
                .map(storeMapper::ToModel)
                .map(storeRepository::save)
                .map(storeMapper::ToDto)
                .orElse(null);
    }
}
