package com.example.springmvcrest.order.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    Long id;
    Set<OrderProductVariantDto> orderProductVariants=new HashSet<>();
    LocalDateTime createAt;
    Long userId;
}
