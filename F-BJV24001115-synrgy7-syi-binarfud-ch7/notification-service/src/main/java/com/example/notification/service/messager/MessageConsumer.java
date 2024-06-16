package com.example.notification.service.messager;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "notification", groupId = "notificationgroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}