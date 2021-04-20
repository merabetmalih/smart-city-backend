package com.example.springmvcrest.politics.domain;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"politics"})
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
    @JoinColumn(name = "politics_id")
    private Politics politics;
}
