package com.example.springmvcrest.offer.api.dto;

import com.example.springmvcrest.offer.domain.OfferType;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OfferCreationDto {
    private Long id;
    private String discountCode;
    private OfferType type;
    private Double newPrice;
    private Integer percentage;
    private Date startDate;
    private Date endDate;
    private Long store;
    private Set<Long> productVariants;
}
