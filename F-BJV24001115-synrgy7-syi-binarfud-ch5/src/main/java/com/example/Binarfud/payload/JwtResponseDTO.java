package com.example.Binarfud.payload;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public JwtResponseDTO(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}