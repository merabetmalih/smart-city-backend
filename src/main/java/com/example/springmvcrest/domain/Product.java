package com.example.springmvcrest.domain;




import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.HashSet;

import java.util.Set;


@Entity
@EqualsAndHashCode(exclude = "parentProduct")
@Indexed()
@Data
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


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentProduct")
    private  Set<Product> subProducts = new HashSet<>();

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Product parentProduct;

    @ManyToMany
    @JoinTable(name = "product_color",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private Set<Color> colors=new HashSet<>();

    @ManyToMany
    @JoinTable(name = "produc_dimension",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "dimension_id"))
    private Set<Dimension> dimensions=new HashSet<>();

    @Lob
    @FullTextField
    private String description;

    @FullTextField
    @Lob
    private String name;

    private Double price;
    private Integer unit;
    private String fabricType;
    @KeywordField
    private String origin;
    @KeywordField
    private String color;
    private String size;
    private String image;


}
