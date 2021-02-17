package com.example.springmvcrest.product.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Product product;

    @OneToMany(mappedBy = "productVariant")
    List<ProductVariantAttributeValue> productVariantAttributeValuesProductVariant=new ArrayList<>();

    private Double price;
    private Integer unit;
    private String image;




}
