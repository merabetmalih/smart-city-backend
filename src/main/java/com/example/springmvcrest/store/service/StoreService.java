package com.example.springmvcrest.store.service;

import com.example.springmvcrest.store.api.dto.StoreDto;
import com.example.springmvcrest.store.api.dto.StoreInformationDto;
import com.example.springmvcrest.store.api.mapper.StoreInformationMapper;
import com.example.springmvcrest.store.api.mapper.StoreMapper;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.store.repository.StoreRepository;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import com.example.springmvcrest.store.service.exception.StoreNotFoundException;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final StoreInformationMapper storeInformationMapper;

    @Named("findStoreByProviderId")
    public Store findStoreByProviderId(Long id){
        return storeRepository.findByProviderId(id)
                .orElseThrow(StoreNotFoundException::new);
    }

    @Named("findStoreById")
    public Store findStoreById(Long id){
        return storeRepository.findById(id)
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

    public Response<String> setStoreInformation(StoreInformationDto storeInformationDto){
        Store store = findStoreByProviderId(storeInformationDto.getProviderId());
        store.setAddress(storeInformationDto.getAddress());
        store.setTelephoneNumber(storeInformationDto.getTelephoneNumber());
        store.setDefaultTelephoneNumber(storeInformationDto.getDefaultTelephoneNumber());
        storeRepository.save(store);
        return new Response<>("created.");
    }

    public StoreInformationDto getStoreInformation(Long providerId){
        return Optional.of(findStoreByProviderId(providerId))
                .map(storeInformationMapper::ToDto)
                .orElseThrow(StoreNotFoundException::new);
    }

    public StoreInformationDto getStoreInformationByStoreId (Long storeId){
        return Optional.of(findStoreById(storeId))
                .map(storeInformationMapper::ToDto)
                .orElseThrow(StoreNotFoundException::new);
    }
}
