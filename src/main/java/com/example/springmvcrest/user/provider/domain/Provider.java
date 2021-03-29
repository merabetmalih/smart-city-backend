package com.example.springmvcrest.user.provider.domain;

import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.store.domain.Store;
import com.example.springmvcrest.user.user.domain.Authority;
import com.example.springmvcrest.user.user.domain.Role;
import com.example.springmvcrest.user.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Provider extends User {

    @OneToMany(mappedBy = "provider",cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private Set<Order> orders = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "provider")
    private Store store;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "provider_role",
            joinColumns = {@JoinColumn(name = "Provider_ID", referencedColumnName = "ID")},
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
