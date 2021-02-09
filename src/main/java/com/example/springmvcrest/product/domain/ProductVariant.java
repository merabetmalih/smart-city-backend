package com.example.springmvcrest.product.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"product"})
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Product product;

    @OneToMany(mappedBy = "productVariant")
    @IndexedEmbedded
    Set<ProductVariantAttributeValue> productVariantAttributeValues=new HashSet<>();

    private Double price;
    private Integer unit;
    private String image;

    public ProductVariant(Product product,Double price,Integer unit){
        this.product=product;
        this.price=price;
        this.unit=unit;
    }



  /*  public void addAttributeValue(AttributeValue attributeValue) {
        ProductVariantAttributeValue productVariantAttributeValue = new ProductVariantAttributeValue( attributeValue,this);
        productVariantAttributeValues.add(productVariantAttributeValue);
        attributeValue.getProductVariantAttributeValues().add(productVariantAttributeValue);
    }*/


}
