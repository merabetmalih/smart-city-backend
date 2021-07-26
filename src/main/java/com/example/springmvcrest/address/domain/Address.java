package com.example.springmvcrest.address.domain;

import com.example.springmvcrest.user.simple.domain.SimpleUser;
import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id", unique = true)
    private Long id;

    private String streetNumber;
    private String admin;
    private String subAdmin;
    private String locality;
    private String streetName;
    private String postalCode;
    private String countryCode;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private String fullAddress;
    private String apartmentNumber;
    private String businessName;
    private String doorCodeName;

    @ManyToOne
    @JoinColumn(name = "simpleUser_id")
    private SimpleUser user;

    private Boolean deleted=false;
}
