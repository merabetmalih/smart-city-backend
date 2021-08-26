package com.example.springmvcrest.store.domain;

import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.flashDeal.domain.PeriodicityFlash;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.policy.domain.Policies;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.user.provider.domain.Provider;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
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


    @OneToOne
    private Provider provider;

    @ManyToMany
    @JoinTable(name = "store_category",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> defaultCategories = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    @IndexedEmbedded
    private Set<CustomCategory> customCategories = new HashSet<>();

    @OneToMany(mappedBy = "store",cascade =  {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<FlashDeal> flashDeals = new HashSet<>();

    @OneToMany(mappedBy = "store",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "store")
    private Policies policies;

    @OneToMany(mappedBy = "store",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Offer> offers = new HashSet<>();

    private Long transmittedFlash;
    private LocalDate lastFlashStart;
    @Enumerated(EnumType.STRING)
    private PeriodicityFlash periodicityFlash;

    @ManyToMany(mappedBy = "followedStores")
    private Set<SimpleUser> followers=new HashSet<SimpleUser>();

}
