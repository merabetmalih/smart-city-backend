package com.example.springmvcrest.product.domain;




import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;


@Entity
@Indexed()
@Data
//@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories=new HashSet<Category>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @IndexedEmbedded
    private Set<Tags> tags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @IndexedEmbedded
    private Set<ProductVariant> productVariants = new HashSet<>();



    @Lob
    @FullTextField
    private String description;

    @FullTextField
    @Lob
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<Images> images = new HashSet<>();


}
