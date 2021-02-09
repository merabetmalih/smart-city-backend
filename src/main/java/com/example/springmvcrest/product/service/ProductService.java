package com.example.springmvcrest.product.service;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.domain.Tags;
import com.example.springmvcrest.product.repository.ProductRepository;
import com.example.springmvcrest.product.repository.ProductVariantRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantRepository productVariantRepository;

    public ProductDTO create(ProductDTO productDTO){
        productDTO.setId(null);
        return Optional.of(productDTO)
                .map(productMapper::ToModel)
                .map(product -> {
                    product.setProductVariants(product.getProductVariants().stream()
                            .map(productVariantRepository::save)
                            .collect(Collectors.toSet()));
                     return product;
                })
                .map(productRepository::save)
                .map(productMapper::ToDto)
                .orElse(null);
    }



}
