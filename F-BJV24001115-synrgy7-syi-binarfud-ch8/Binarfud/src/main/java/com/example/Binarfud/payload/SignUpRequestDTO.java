package com.example.Binarfud.payload;

import com.example.Binarfud.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequestDTO {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
