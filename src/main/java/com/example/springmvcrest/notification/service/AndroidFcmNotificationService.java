package com.example.springmvcrest.notification.service;


import com.example.springmvcrest.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AndroidFcmNotificationService implements NotificationService{
    private final FCMService fcmService;

    public void sendNotificationWithoutData(Notification notification) {
        try {
            fcmService.sendMessageWithoutData(notification);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public void sendNotificationToToken(Notification notification) {
        try {
            fcmService.sendMessageToToken(notification);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}