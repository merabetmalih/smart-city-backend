package com.example.springmvcrest.product.domain;


import com.example.springmvcrest.order.domain.OrderProductVariant;
import com.example.springmvcrest.user.simple.domain.Cart;
import com.example.springmvcrest.user.simple.domain.CartProductVariant;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private Product product;

    @OneToMany(mappedBy = "productVariant",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    List<ProductVariantAttributeValue> productVariantAttributeValuesProductVariant=new ArrayList<>();


    @OneToMany(mappedBy = "cartProductVariant",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<CartProductVariant> cartProductVariants=new HashSet<>();

    @OneToMany(mappedBy = "orderProductVariant",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<OrderProductVariant> orderProductVariants=new HashSet<>();

    private Double price;
    private Integer unit;
    private String image;




}
