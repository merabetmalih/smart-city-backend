package com.example.springmvcrest.product.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude ={"products","parentCategory"} )
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private Set<Category> subCategorys = new HashSet<>();

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Category parentCategory;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products=new HashSet<Product>();

    public Category(String name) {
        this.name = name;
    }


}
