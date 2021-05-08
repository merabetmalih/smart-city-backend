package com.example.springmvcrest.bill.api;

import com.example.springmvcrest.order.domain.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillTotalDto {
    private Long policyId;
    private Double total;
    private OrderType orderType;
}
