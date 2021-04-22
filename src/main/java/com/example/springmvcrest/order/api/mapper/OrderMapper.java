package com.example.springmvcrest.order.api.mapper;

import com.example.springmvcrest.order.api.dto.OrderCreationDto;
import com.example.springmvcrest.order.api.dto.OrderDto;
import com.example.springmvcrest.order.domain.Order;
import com.example.springmvcrest.store.service.StoreService;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderProductVariantMapper.class,SimpleUserService.class, StoreService.class})
public interface OrderMapper {

    @Mapping(source = "order.user.id", target = "userId")
    OrderDto toDto(Order order);

    @Mapping(source = "orderCreationDto.userId", target = "user")
    @Mapping(source = "orderCreationDto.storeId", target = "store", qualifiedByName = "findStoreById")
    Order toModel(OrderCreationDto orderCreationDto);

}
