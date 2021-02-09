package com.example.springmvcrest.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"attributeValue","productVariant"})
@Builder
public class ProductVariantAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "attributeValue_id")
    @JsonBackReference
    AttributeValue attributeValue;

    @ManyToOne
    @JoinColumn(name = "productVariant_id")
    @JsonBackReference
    ProductVariant productVariant;

    public ProductVariantAttributeValue(AttributeValue attributeValue, ProductVariant productVariant) {
        this.attributeValue = attributeValue;
        this.productVariant = productVariant;
    }
}
