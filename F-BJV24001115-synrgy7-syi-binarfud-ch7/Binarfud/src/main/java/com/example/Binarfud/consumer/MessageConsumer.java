package com.example.Binarfud.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "order", groupId = "binarfudgroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

}