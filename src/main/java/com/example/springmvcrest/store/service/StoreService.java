package com.example.springmvcrest.store.service;

import com.example.springmvcrest.store.api.StoreDto;
import com.example.springmvcrest.store.api.StoreMapper;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.repository.StoreRepository;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import com.example.springmvcrest.store.service.exception.StoreNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final StoreMapper storeMapper;

    public Store findStoreByProviderId(Long id){
        return storeRepository.findByProviderId(id)
                .orElseThrow(StoreNotFoundException::new);
    }

    private Boolean hasStore(Long id){
        return storeRepository.findByProviderId(id)
                .isPresent();
    }

    public StoreDto create(StoreDto storeDto) {
        return Optional.of(storeDto)
                .map(storeMapper::ToModel)
                .filter(store -> !hasStore(store.getProvider().getId()))
                .map(storeRepository::save)
                .map(storeMapper::ToDto)
                .orElseThrow(MultipleStoreException::new);
    }


}
