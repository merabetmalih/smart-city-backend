package com.example.springmvcrest.offer.api.dto;

import com.example.springmvcrest.offer.domain.OfferType;
import lombok.Data;

import java.util.Date;

@Data
public class OfferVariantDto {
    private String discountCode;
    private OfferType type;
    private Double newPrice;
    private Integer percentage;
    private Date startDate;
    private Date endDate;
}
