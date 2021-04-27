package com.example.springmvcrest.policy.api.dto;

import com.example.springmvcrest.policy.domain.SelfPickUpOptions;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PoliciesDto {
    private Boolean delivery;
    private SelfPickUpOptions selfPickUpOption;
    private Long validDuration;
    private Integer tax;
    private Set<TaxRangeDto> taxRanges=new HashSet<>();
    private Long providerId;
}
