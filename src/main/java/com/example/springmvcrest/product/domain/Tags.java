package com.example.springmvcrest.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"product"})
@Entity
//@NoArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField(analyzer = "stop" ,searchable= Searchable.YES)
    private String name;

    @ManyToOne
    @JsonBackReference
    private Product product;

    public Tags(String name,Product product) {
        this.name = name;
        this.product=product;
    }
    public Tags(String name) {
        this.name = name;
    }

    public Tags() {
    }
}
