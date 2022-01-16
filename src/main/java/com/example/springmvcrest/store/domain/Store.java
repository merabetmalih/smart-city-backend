package com.example.springmvcrest.store.domain;


import com.example.springmvcrest.product.domain.Category;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE} , mappedBy = "store")
    private StoreAddress storeAddress;

    private String telephoneNumber;

    private String defaultTelephoneNumber;

    private String imageStore;




    @ManyToMany
    @JoinTable(name = "store_category",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> defaultCategories = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    @IndexedEmbedded
    private Set<Category> Categories = new HashSet<>();


    private LocalDate lastFlashStart;

}

