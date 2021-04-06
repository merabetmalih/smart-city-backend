package com.example.springmvcrest.notification.configuration;

public enum NotificationParameter {
    SOUND("default"),
    COLOR("#FFFFFF");

    private String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}