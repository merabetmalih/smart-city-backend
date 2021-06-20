package com.example.springmvcrest.order.domain;

import com.example.springmvcrest.offer.domain.Offer;
import com.example.springmvcrest.product.domain.ProductVariant;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"order","orderProductVariant","offer"})
@Builder
@Entity
public class OrderProductVariant {
    @EmbeddedId
    private OrderProductVariantId id;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @MapsId("orderProductVariantId")
    @ManyToOne
    @JoinColumn(name = "productVariant_id")
    private ProductVariant orderProductVariant;

    private Integer quantity;

    @OneToOne(cascade ={CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
