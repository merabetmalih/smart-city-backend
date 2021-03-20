package com.example.springmvcrest.user.simple.domain;

import com.example.springmvcrest.product.domain.ProductVariant;
import lombok.*;


import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cart","cartProductVariant"})
@Builder
public class CartProductVariant {

    @EmbeddedId
    private CartProductVariantId id;

    @MapsId("cartId")
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @MapsId("cartProductVariantId")
    @ManyToOne
    @JoinColumn(name = "productVariant_id")
    private ProductVariant cartProductVariant;

    private Integer unit;


}
