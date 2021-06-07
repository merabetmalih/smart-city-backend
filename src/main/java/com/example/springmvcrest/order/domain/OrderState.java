package com.example.springmvcrest.order.domain;

import lombok.*;

import javax.persistence.*;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_state")
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private boolean newOrder = false;
    private boolean accepted = false;
    private boolean rejected = false;
    private boolean ready = false;
    private boolean delivered = false;
    private boolean pickedUp = false;
    private boolean confirmedDelivered = false;
    private boolean confirmedPickedUp = false;
   /* private boolean canceled = false;
    private boolean archived = false;
    private boolean archivedProblem = false;*/
}