package com.example.springmvcrest.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String userName;
    private String passWord1;
    private String passWord2;

    @ManyToMany
    @JoinTable(name = "users_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<Category> categories= new HashSet<>();

    public User(String email, String userName, String passWord1, String passWord2, Set<Category> categories) {
        this.email = email;
        this.userName = userName;
        this.passWord1 = passWord1;
        this.passWord2 = passWord2;
        this.categories = categories;
    }
    public User(){

    }
}
