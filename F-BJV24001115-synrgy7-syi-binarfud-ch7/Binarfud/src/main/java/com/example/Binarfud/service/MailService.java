package com.example.Binarfud.service;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final Environment env;
    private final JavaMailSender javaMailSender;

    public MailService(Environment env, JavaMailSender javaMailSender) {
        this.env = env;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String email, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(env.getProperty("spring.mail.username"));
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(message);
        javaMailSender.send(msg);
    }

    public void sendOTP(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        javaMailSender.send(message);
    }
}
