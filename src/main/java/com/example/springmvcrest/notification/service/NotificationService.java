package com.example.springmvcrest.notification.service;

import com.example.springmvcrest.notification.domain.Notification;

public interface NotificationService {
    public void sendNotification(Notification notification);
}
