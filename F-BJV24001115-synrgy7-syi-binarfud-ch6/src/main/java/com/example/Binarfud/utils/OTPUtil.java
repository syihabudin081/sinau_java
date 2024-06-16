package com.example.Binarfud.utils;

import org.springframework.stereotype.Component;

@Component
public class OTPUtil {
    private static final int otpLength = 6;

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append((int) (Math.random() * 10));
        }
        return otp.toString();
    }
}
