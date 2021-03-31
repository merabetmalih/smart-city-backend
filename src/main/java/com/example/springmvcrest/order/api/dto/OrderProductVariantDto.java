package com.example.springmvcrest.order.api.dto;

import com.example.springmvcrest.order.domain.OrderProductVariantId;
import com.example.springmvcrest.product.api.dto.ImagesDTO;
import com.example.springmvcrest.product.api.dto.ProductVariantDto;
import lombok.Data;

@Data
public class OrderProductVariantDto {
    private OrderProductVariantId id;
    private ProductVariantDto productVariant;
    private Integer quantity;
    private ImagesDTO productImage;
    private String productName;
}
