package com.example.springmvcrest.product.service;

import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.product.repository.ProductVariantRepository;
import com.example.springmvcrest.store.service.exception.MultipleStoreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;

    public ProductVariant findById(Long id)
    {
        return productVariantRepository.findById(id)
                .orElseThrow(MultipleStoreException::new);
    }
}
