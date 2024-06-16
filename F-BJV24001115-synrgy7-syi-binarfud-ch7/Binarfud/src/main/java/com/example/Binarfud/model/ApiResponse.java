package com.example.Binarfud.model;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;

    public ApiResponse(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    // Getters and setters
}