package com.example.springmvcrest.order.api.dto;

import lombok.Data;

@Data
public class OrderStateDto {
     boolean newOrder = false;
     boolean accepted = false;
     boolean rejected = false;
     boolean ready = false;
     boolean delivered = false;
     boolean pickedUp = false;
     boolean received = false;
}
