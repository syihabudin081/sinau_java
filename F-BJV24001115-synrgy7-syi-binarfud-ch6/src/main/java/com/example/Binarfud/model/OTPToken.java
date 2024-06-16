package com.example.Binarfud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "otp_tokens")
public class OTPToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String otp;
    private LocalDateTime expiryDate;

    public OTPToken(String email, String otp, LocalDateTime expiryDate) {
        this.email = email;
        this.otp = otp;
        this.expiryDate = expiryDate;
    }

    public OTPToken() {
    }
}
