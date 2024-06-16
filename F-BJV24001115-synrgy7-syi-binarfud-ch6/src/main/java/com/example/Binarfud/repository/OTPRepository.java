package com.example.Binarfud.repository;

import com.example.Binarfud.model.OTPToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OTPRepository extends JpaRepository<OTPToken, UUID> {
    Optional<OTPToken> findByEmailAndOtp(String email, String otp);
}
