package com.example.springmvcrest.order.domain;

import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user","store"})
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "simpleUser_id")
    private SimpleUser user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<OrderProductVariant> orderProductVariants=new HashSet<>();

    private LocalDateTime createAt;

    private Double total;
}
