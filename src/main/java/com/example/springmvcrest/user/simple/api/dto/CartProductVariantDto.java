package com.example.springmvcrest.user.simple.api.dto;

import com.example.springmvcrest.product.api.dto.ProductVariantDto;
import com.example.springmvcrest.user.simple.domain.CartProductVariantId;
import lombok.Data;

@Data
public class CartProductVariantDto {
    private CartProductVariantId id;
    private ProductVariantDto productVariantDto;
    private Integer unit;
}
