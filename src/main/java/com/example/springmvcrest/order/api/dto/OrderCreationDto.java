package com.example.springmvcrest.order.api.dto;

import com.example.springmvcrest.address.api.AddressDto;
import com.example.springmvcrest.order.domain.OrderType;
import com.example.springmvcrest.user.simple.domain.CartProductVariantId;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreationDto {
    Long userId;
    Long storeId;
    OrderType orderType;
    AddressDto address;
    List<CartProductVariantId> cartProductVariantIds;
    String receiverFirstName;
    String receiverLastName;
    String receiverBirthDay;
}
