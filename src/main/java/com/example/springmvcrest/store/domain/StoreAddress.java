package com.example.springmvcrest.store.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StoreAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne
    private Store store;
}
