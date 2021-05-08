package com.example.springmvcrest.bill.doamin;

import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.order.domain.OrderType;
import com.example.springmvcrest.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double total;

    private Double alreadyPaid;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
