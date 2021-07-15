package com.example.springmvcrest.store.service;

import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.service.CategoryService;
import com.example.springmvcrest.store.api.dto.*;
import com.example.springmvcrest.store.api.mapper.StoreAddressMapper;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final StoreInformationMapper storeInformationMapper;
    private final CategoryService categoryService;
    private final StoreAddressMapper storeAddressMapper;


    @Named("getStoreName")
    public String getStoreName(Store store) { return  store.getName(); }

    @Named("getStoreAddress")
    public String getStoreAddress(Store store) { return  store.getStoreAddress().getFullAddress(); }

    @Named("getStoreAddressUpdated")
    public StoreAddressDto getStoreAddressUpdated(Store store) {
        return  storeAddressMapper.ToDto(
                store.getStoreAddress()
        );
    }

    @Named("getStoreFollowers")
    public Integer getStoreFollowers(Store store) {
        return  store.getFollowers().size();
    }

    @Named("getStoreId")
    public Long getStoreId(Store store) { return  store.getId(); }

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

    public Store saveStore(Store store){
        return storeRepository.save(store);
    }

    private Boolean hasStore(Long id){
        return storeRepository.findByProviderId(id)
                .isPresent();
    }

    public StoreCreationDto create(StoreCreationDto storeCreationDto) {
        return Optional.of(storeCreationDto)
                .map(storeMapper::ToModel)
                .filter(store -> !hasStore(store.getProvider().getId()))
                .map(this::setStoreAddress)
                .map(storeRepository::save)
                .map(storeMapper::ToDto)
                .orElseThrow(MultipleStoreException::new);
    }

    private Store setStoreAddress(Store store){
        store.getStoreAddress().setStore(store);
        return store;
    }

    public Response<String> setStoreInformation(StoreInformationCreationDto storeInformationDto){
        Store store = findStoreByProviderId(storeInformationDto.getProviderId());
       // store.setAddress(storeInformationDto.getAddress());
        store.setTelephoneNumber(storeInformationDto.getTelephoneNumber());
        store.setDefaultTelephoneNumber(storeInformationDto.getDefaultTelephoneNumber());

        Set<Category> collect = storeInformationDto.getDefaultCategories().stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet());
        store.setDefaultCategories(collect);
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

    public List<StoreDto> findStoreByDistance(double latitude, double longitude, double distance) {
        return storeRepository.findStoreAround(latitude, longitude, distance)
                .stream()
                .map(storeMapper::toDto)
                .collect(Collectors.toList());
    }
}
