package com.example.springmvcrest.sse.events;

import lombok.*;


@Getter
@Setter
public class OrderChangeEvent extends Event {

    public OrderChangeEvent(Object source, String name) {
        super(source);
        this.name = name;
    }
}
