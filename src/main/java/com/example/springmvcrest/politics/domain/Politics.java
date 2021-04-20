package com.example.springmvcrest.politics.domain;

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
public class Politics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean delivery;

    @Enumerated(EnumType.STRING)
    private SelfPickUpOptions selfPickUpOption;

    private Long duration;

    private Integer tax;

    @OneToMany(mappedBy = "politics",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<TaxRange> taxRanges=new HashSet<>();

    @OneToOne
    private Store store;
}
