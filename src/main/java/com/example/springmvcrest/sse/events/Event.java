package com.example.springmvcrest.sse.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class Event extends ApplicationEvent {
    public String name;

    public Event(Object source) {
        super(source);
    }
}
