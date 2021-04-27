package com.example.springmvcrest.policy.domain;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"policies"})
@Builder
@Entity
public class TaxRange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double startRange;
    private Double endRange;
    private Integer fixAmount;

    @ManyToOne
    @JoinColumn(name = "policies_id")
    private Policies policies;
}
