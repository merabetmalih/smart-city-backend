package com.example.springmvcrest.store.domain;

import com.example.springmvcrest.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"store"})
@Builder
@Entity
    public class CustomCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JsonBackReference
    private Store store;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customCategory")
    @IndexedEmbedded
    private Set<Product> products = new HashSet<>();
}
