package com.example.springmvcrest.store.domain;

import com.example.springmvcrest.user.provider.domain.Provider;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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



    @OneToOne
    @JsonBackReference
    private Provider provider;

    @ManyToMany
    @JoinTable(name = "store_categoryStore",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "categoryStore_id"))
    private Set<CategoryStore> categories=new HashSet<CategoryStore>();
}
