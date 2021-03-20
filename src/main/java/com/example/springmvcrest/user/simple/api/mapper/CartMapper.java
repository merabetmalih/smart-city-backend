package com.example.springmvcrest.user.simple.api.mapper;

import com.example.springmvcrest.user.simple.api.dto.CartDto;
import com.example.springmvcrest.user.simple.domain.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartProductVariantMapper.class})
public interface CartMapper {

    CartDto toDto(Cart cart);

    Cart toModel(CartDto cartDto);
}
