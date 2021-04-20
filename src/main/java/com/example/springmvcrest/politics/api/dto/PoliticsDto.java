package com.example.springmvcrest.politics.api.dto;

import com.example.springmvcrest.politics.domain.SelfPickUpOptions;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PoliticsDto {
    private Boolean delivery;
    private SelfPickUpOptions selfPickUpOption;
    private Long duration;
    private Integer tax;
    private Set<TaxRangeDto> taxRanges=new HashSet<>();
    private Long providerId;
}
