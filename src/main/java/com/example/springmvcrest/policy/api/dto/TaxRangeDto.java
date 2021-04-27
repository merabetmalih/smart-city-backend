package com.example.springmvcrest.policy.api.dto;

import lombok.Data;

@Data
public class TaxRangeDto {
    private Double startRange;
    private Double endRange;
    private Integer fixAmount;
}
