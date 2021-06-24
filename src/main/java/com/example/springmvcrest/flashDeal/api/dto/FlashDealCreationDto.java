package com.example.springmvcrest.flashDeal.api.dto;

import lombok.Data;

@Data
public class FlashDealCreationDto {
    private Long id;
    private String title;
    private String content;
    private Long providerId;
}
