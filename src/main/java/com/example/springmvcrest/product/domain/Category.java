package com.example.springmvcrest.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "products")
@Entity
//@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long parenetId;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    private Set<Product> products=new HashSet<Product>();

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }
}
