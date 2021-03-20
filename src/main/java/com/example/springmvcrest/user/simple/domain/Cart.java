package com.example.springmvcrest.user.simple.domain;

import com.example.springmvcrest.product.domain.ProductVariant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private SimpleUser simpleUser;


    @OneToMany(mappedBy = "cart",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    Set<CartProductVariant> cartProductVariants=new HashSet<>();
}
