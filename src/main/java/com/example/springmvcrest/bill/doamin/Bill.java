package com.example.springmvcrest.bill.doamin;

import com.example.springmvcrest.order.domain.OrderType;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private Double total;
}
