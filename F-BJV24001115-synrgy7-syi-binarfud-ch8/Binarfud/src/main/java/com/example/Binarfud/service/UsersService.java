package com.example.Binarfud.service;

import com.example.Binarfud.model.ERole;
import com.example.Binarfud.model.Role;
import com.example.Binarfud.payload.UserDTO;
import com.example.Binarfud.model.Users;
import com.example.Binarfud.repository.RoleRepository;
import com.example.Binarfud.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.Binarfud.model.ERole.ROLE_USER;
import static java.lang.Boolean.TRUE;

@Service
@Slf4j
public class UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public ResponseEntity<?> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    public void createUserPostLogin(String username, String email) {
        Role role = roleRepository.findByName(ROLE_USER);
        if (role == null) {
            // Handle the case where the role is not found
            log.error("Role ROLE_USER not found in the database");
            throw new RuntimeException("Role ROLE_USER not found in the database");
        }
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));
        log.info("Creating user with username: {} and email: {} and role: {}", username, email, roles);
        Users user = getByUsername(email);
        if (user == null) {
            user = Users.builder()
                    .username(username)
                    .emailAddress(email)
                    .roles(roles)
                    .build();
            usersRepository.save(user);
        }
    }

    public ResponseEntity<?> getUserById(UUID id) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(convertToDto(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> saveUser(UserDTO userDto) {
        Set<Role> roles = userDto.getRole();
        Users user = new Users(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), roles);
        usersRepository.save(user);
        return ResponseEntity.ok(convertToDto(user));
    }

    public void verifyUser(String email) {
        Optional<Users> userOptional = usersRepository.findByEmailAddress(email);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setVerified(true);
            Users savedUser = usersRepository.save(user);
            if (savedUser.isVerified()) {
                // Verifikasi berhasil disimpan ke basis data
                // Lanjutkan dengan logika yang sesuai
                log.info("User verification successful for email: {}", email);
            } else {
                // Verifikasi tidak berhasil disimpan ke basis data
                // Luncurkan pengecualian atau tindakan yang sesuai
                log.error("Failed to verify user for email: {}", email);
                throw new RuntimeException("Failed to verify user");
            }
        } else {
            // Pengguna dengan alamat email yang diberikan tidak ditemukan
            // Luncurkan pengecualian atau tindakan yang sesuai
            log.error("User not found with email: {}", email);
            throw new EntityNotFoundException("User not found with email: " + email);
        }
    }

    public ResponseEntity<?> deleteUser(UUID id) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            usersRepository.delete(user.get());
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmailAddress(email);
    }

    private UserDTO convertToDto(Users user) {
        return new UserDTO(user.getUsername(), user.getEmailAddress(), user.getPassword(), user.getRoles());
    }

    public ResponseEntity<?> updateUser(UUID id, UserDTO userDto) {
        Optional<Users> user = usersRepository.findById(id);
        if (user.isPresent()) {
            user.get().setUsername(userDto.getUsername());
            user.get().setEmailAddress(userDto.getEmail());
            user.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.get().setRoles(userDto.getRole());
            usersRepository.save(user.get());
            return ResponseEntity.ok(convertToDto(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Users getByUsername(String username) {
        Optional<Users> userOptional = usersRepository.findByUsername(username);
        return userOptional.orElse(null);
    }
}
