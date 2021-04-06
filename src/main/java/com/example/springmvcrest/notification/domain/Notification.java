package com.example.springmvcrest.notification.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private String title;
    private String message;
    private String topic;
    private String token;
}
