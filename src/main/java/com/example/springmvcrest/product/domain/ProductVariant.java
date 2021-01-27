package com.example.springmvcrest.product.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"product"})
@Entity
//@NoArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Product product;

    @ManyToMany
    @JoinTable(name = "productVariant_attributeValue",
            joinColumns = @JoinColumn(name = "productVariant_id"),
            inverseJoinColumns = @JoinColumn(name = "attributeValue_id"))
    private Set<AttributeValue> attributeValues=new HashSet<AttributeValue>();

    private Double price;
    private Integer unit;

    public ProductVariant(Product product,Double price,Integer unit){
        this.product=product;
        this.price=price;
        this.unit=unit;
    }

    public ProductVariant() {
    }
}
