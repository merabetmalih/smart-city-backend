package com.example.springmvcrest.policy.domain;

import com.example.springmvcrest.store.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"store"})
@Builder
@Entity
public class Policies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean delivery;

    @Enumerated(EnumType.STRING)
    private SelfPickUpOptions selfPickUpOption;

    private Long validDuration;

    private Integer tax;

    @OneToMany(mappedBy = "policies",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<TaxRange> taxRanges=new HashSet<>();

    @OneToOne
    private Store store;
}
