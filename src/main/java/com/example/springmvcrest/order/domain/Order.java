package com.example.springmvcrest.order.domain;

import com.example.springmvcrest.address.domain.Address;
import com.example.springmvcrest.bill.doamin.Bill;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @OneToOne(cascade ={CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_state")
    private OrderState orderState;

    private String receiverFirstName;

    private String receiverLastName;

    private String receiverBirthDay;

    private LocalDateTime createAt;

    private Long validDuration;

    @Lob
    private String providerComment;
    private Date providerDate;
}
