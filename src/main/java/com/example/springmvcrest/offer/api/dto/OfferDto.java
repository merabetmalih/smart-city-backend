package com.example.springmvcrest.offer.api.dto;

import com.example.springmvcrest.offer.domain.OfferType;
import com.example.springmvcrest.product.api.dto.ProductVariantDto;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OfferDto {
    private Long id;
    private String discountCode;
    private OfferType type;
    private Double newPrice;
    private Integer percentage;
    private Date startDate;
    private Date endDate;
    private Set<ProductVariantDto> productVariants;
}
