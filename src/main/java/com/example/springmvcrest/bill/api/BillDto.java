package com.example.springmvcrest.bill.api;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillDto {
    private Double total;
    private Double alreadyPaid;
    private LocalDateTime createdAt;
}
