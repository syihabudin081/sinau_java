package com.example.notification.service.socketio;


import lombok.Data;

@Data
public class BinarPromoMessage {
    private String from;
    private String product;
    private String price;
}