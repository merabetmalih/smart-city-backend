package com.example.springmvcrest.order.api.dto;

import lombok.Data;

@Data
public class OrderStateDto {
     boolean newOrder;
     boolean accepted;
     boolean rejected;
     boolean delivered;
     boolean pickedUp;
     boolean confirmedDelivered;
     boolean confirmedPickedUp;
     boolean canceled;
     boolean archived;
     boolean archivedProblem;
}
