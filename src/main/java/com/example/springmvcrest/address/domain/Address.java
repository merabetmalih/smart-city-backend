package com.example.springmvcrest.address.domain;

import com.example.springmvcrest.user.simple.domain.SimpleUser;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private Long zipCode;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coordinate_id")
    private Coordinate coordinate;

    @ManyToOne
    @JoinColumn(name = "simpleUser_id")
    private SimpleUser user;

    private Boolean deleted=false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return houseNumber == address.houseNumber &&
                Objects.equals(id, address.id) &&
                Objects.equals(street, address.street) &&
                Objects.equals(city, address.city) &&
                Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseNumber, street, city, zipCode);
    }

    @Override
    public String toString() {
        return houseNumber + " " + street + " " + city + " " + zipCode;
    }
}
