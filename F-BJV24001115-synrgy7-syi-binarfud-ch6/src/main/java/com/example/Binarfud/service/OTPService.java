package com.example.Binarfud.service;

import com.example.Binarfud.model.OTPToken;
import com.example.Binarfud.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    public void saveOTP(String email, String otp) {
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
        OTPToken otpToken = new OTPToken(email, otp, expiryDate);
        otpRepository.save(otpToken);
    }

    public boolean validateOTP(String email, String otp) {
        Optional<OTPToken> otpTokenOptional = otpRepository.findByEmailAndOtp(email, otp);
        if (otpTokenOptional.isPresent()) {
            OTPToken otpToken = otpTokenOptional.get();
            if (otpToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }
}
