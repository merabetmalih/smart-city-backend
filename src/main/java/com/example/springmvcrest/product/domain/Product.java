package com.example.springmvcrest.product.domain;




import com.example.springmvcrest.store.domain.CustomCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.HashSet;

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
    @IndexedEmbedded
    private Set<Tags> tags = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "product")
    @IndexedEmbedded
    private Set<ProductVariant> productVariants = new HashSet<>();


    @ManyToOne
    @JsonBackReference
    private CustomCategory customCategory;

    @Lob
    @FullTextField
    private String description;

    @FullTextField
    @Lob
    private String name;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<Images> images = new HashSet<>();


}
