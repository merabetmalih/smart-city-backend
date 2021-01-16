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
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String colorName;

    @ManyToMany(mappedBy = "colors")
    @JsonBackReference
    private Set<Product> products=new HashSet<>();

    public Color(String colorName) {
        this.colorName=colorName;
    }
}
