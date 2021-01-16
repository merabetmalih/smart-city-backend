package com.example.springmvcrest.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "products")
@Entity
@NoArgsConstructor
public class Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dimensionValue;

    @ManyToMany(mappedBy = "dimensions")
    @JsonBackReference
    private Set<Product> products=new HashSet<>();

    public Dimension(String dimensionValue) {
        this.dimensionValue=dimensionValue;
    }

}
