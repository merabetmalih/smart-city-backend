package com.example.springmvcrest.offer.api.dto;

import com.example.springmvcrest.offer.domain.OfferState;
import com.example.springmvcrest.offer.domain.OfferType;
import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.domain.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OfferDto {
    private Long id;
    private String discountCode;
    private OfferType type;
    private Double newPrice;
    private Integer percentage;
    private Date startDate;
    private Date endDate;
    private List<ProductDTO> products;
    private OfferState offerState;
}
