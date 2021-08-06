package com.example.springmvcrest.flashDeal.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlashDealDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private String storeName;
    private String storeAddress;
    private Double latitude;
    private Double longitude;
}
