package com.example.springmvcrest.order.api.dto;

import com.example.springmvcrest.address.api.AddressDto;
import com.example.springmvcrest.order.domain.OrderType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    Long id;
    Set<OrderProductVariantDto> orderProductVariants=new HashSet<>();
    Long userId;
    LocalDateTime createAt;
    Double total;
    OrderType orderType;
    Long validDuration;
    AddressDto address;
}
