package com.example.springmvcrest.product.domain;


import com.example.springmvcrest.store.domain.CustomCategory;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Indexed()
@Data
@EqualsAndHashCode(exclude = {"customCategory"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories=new HashSet<Category>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")

    private Set<Tags> tags = new HashSet<>();

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.REMOVE}, mappedBy = "product")

    private List<ProductVariant> productVariants = new ArrayList<>();


    @ManyToOne

    private CustomCategory customCategory;

    @OneToMany
    @JoinColumn(name="product")
    private Set<Attribute> attributes = new HashSet<>();

    @Lob
    @FullTextField
    private String description;

    @FullTextField
    @Lob
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Images> images = new ArrayList<>();


}
