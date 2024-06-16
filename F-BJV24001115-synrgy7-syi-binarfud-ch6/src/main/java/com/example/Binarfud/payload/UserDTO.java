package com.example.Binarfud.payload;

import com.example.Binarfud.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private Set<Role> role;


    public UserDTO(String username, String email, String password, Set<Role> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
