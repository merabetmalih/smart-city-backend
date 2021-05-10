package com.example.springmvcrest.bill.api;

import lombok.Data;

@Data
public class BillDto {
    private Double total;
    private Double alreadyPaid;
    private String createdAt;
}
