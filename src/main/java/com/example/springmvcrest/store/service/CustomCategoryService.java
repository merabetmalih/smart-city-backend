package com.example.springmvcrest.store.service;

import com.example.springmvcrest.store.api.dto.CustomCategoryDto;
import com.example.springmvcrest.store.api.mapper.CustomCategoryMapper;
import com.example.springmvcrest.store.domain.CustomCategory;
import com.example.springmvcrest.store.repository.CustomCategoryRepository;
import com.example.springmvcrest.store.service.exception.CustomCategoryNotFoundExeption;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomCategoryService {
    private final CustomCategoryRepository customCategoryRepository;
    private final CustomCategoryMapper customCategoryMapper;

    public CustomCategoryDto create(CustomCategoryDto customCategoryDto){
        customCategoryDto.setId(null);
        return Optional.of(customCategoryDto)
                .map(customCategoryMapper::ToModel)
                .map(customCategoryRepository::save)
                .map(customCategoryMapper::ToDto)
                .orElse(null);
    }

    public CustomCategory findById(Long id){
        return customCategoryRepository.findById(id)
                .orElse(null);
    }

    public Response<String> delete(Long id){
        customCategoryRepository.findById(id)
                .orElseThrow(CustomCategoryNotFoundExeption::new);
        customCategoryRepository.deleteById(id);
        return new Response<>("deleted.");
    }

    public CustomCategoryDto update(CustomCategoryDto customCategoryDto){
        return Optional.of(customCategoryDto)
                .map(customCategoryMapper::ToModel)
                .map(customCategoryRepository::save)
                .map(customCategoryMapper::ToDto)
                .orElse(null);
    }

    public List<CustomCategoryDto> getAllByProvider(Long id){
        return customCategoryRepository.findByStore_Provider_Id(id)
                .stream()
                .map(customCategoryMapper::ToDto)
                .collect(Collectors.toList());
    }
}
