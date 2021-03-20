package com.example.springmvcrest.user.simple.api.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    Set<CartProductVariantDto> cartProductVariants=new HashSet<>();
}
