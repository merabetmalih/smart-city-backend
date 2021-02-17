package com.example.springmvcrest.product.service;


import com.example.springmvcrest.product.api.dto.AttributeDto;

import com.example.springmvcrest.product.repository.AttributeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AttributeService {
  /*  private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeDto create(AttributeDto attributeDto){
        return Optional.of(attributeDto)
                .map(attributeMapper::ToModel)
                .map(attributeRepository::save)
                .map(attributeMapper::ToDto)
                .orElse(null);
    }*/
}
