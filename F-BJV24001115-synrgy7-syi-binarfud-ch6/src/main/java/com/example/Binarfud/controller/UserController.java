package com.example.Binarfud.controller;
import com.example.Binarfud.model.Users;
import com.example.Binarfud.payload.UserDTO;
import com.example.Binarfud.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return usersService.getUserById(id);
             }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        return usersService.saveUser(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDTO user) {
        return usersService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        return usersService.deleteUser(id);
    }
}
