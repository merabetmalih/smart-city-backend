package com.example.springmvcrest.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"productVariants","attribute"})
@Entity
//@NoArgsConstructor
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String image;

    @ManyToMany(mappedBy = "attributeValues")
    @JsonBackReference
    private Set<ProductVariant> productVariants=new HashSet<ProductVariant>();

    @ManyToOne
    @IndexedEmbedded
    private Attribute attribute;

    public AttributeValue(Attribute attribute){
        this.attribute=attribute;
    }

    public AttributeValue() {
    }
}
