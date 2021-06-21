package com.example.springmvcrest.offer.domain;

import com.example.springmvcrest.product.domain.ProductVariant;
import com.example.springmvcrest.store.domain.Store;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"store"})
@Builder
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String discountCode;

    @Enumerated(EnumType.STRING)
    private OfferType type;

    private Double newPrice;
    private Integer percentage;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToMany
    @JoinTable(name = "offer_product_variant",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "productVariant_id"))
    private Set<ProductVariant> productVariants=new HashSet<ProductVariant>();

    private Boolean deleted=false;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Offer parentOffer;
}
