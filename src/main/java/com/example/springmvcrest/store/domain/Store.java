package com.example.springmvcrest.store.domain;

import com.example.springmvcrest.user.provider.domain.Provider;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    private String description;

    @Lob
    private String address;

    private String imageStore;


    @OneToOne
    @JsonBackReference
    private Provider provider;

    @ManyToMany
    @JoinTable(name = "store_defaultCategory",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "defaultCategory_id"))
    private Set<DefaultCategory> defaultCategories =new HashSet<DefaultCategory>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    @IndexedEmbedded
    private Set<CustomCategory> customCategories = new HashSet<>();
}
