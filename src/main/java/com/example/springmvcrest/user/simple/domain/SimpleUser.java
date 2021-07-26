package com.example.springmvcrest.user.simple.domain;

import com.example.springmvcrest.address.domain.Address;
import com.example.springmvcrest.flashDeal.domain.FlashDeal;
import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.user.domain.Authority;
import com.example.springmvcrest.user.user.domain.Role;
import com.example.springmvcrest.user.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class SimpleUser extends User {

    private String firstName;
    private String lastName;
    private Date birthDay;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private City defaultCity;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Address> addressSet=new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "simpleUser")
    private Cart cart;

    @ManyToMany
    @JoinTable(name = "simple_users_category",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> interestCenter = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "simple_users_flash_deals",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "flashDeals_id"))
    private Set<FlashDeal> flashDeals = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "simple_users_offers",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "offer_id"))
    private Set<Offer> offers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "simple_users_store",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> followedStores = new HashSet<>();

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "simple_user_role",
            joinColumns = {@JoinColumn(name = "SimpleUser_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<Role> roles=new HashSet<Role>();

    @Transient
    private Set<Authority> authorities=new HashSet<Authority>();

    public Set<Authority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Builder.Default
    private Boolean accountNonExpired = true;

    @Builder.Default
    private Boolean accountNonLocked = true;

    @Builder.Default
    private Boolean credentialsNonExpired = true;

    @Builder.Default
    private Boolean enabled = true;
}
