package com.example.Binarfud.service;

import com.example.Binarfud.model.Users;
import com.example.Binarfud.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ResponseEntity<?> getAllUsers() {
        try {
            List<Users> users = usersRepository.findAll();
            log.info("Users: {}", users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get users", e);
            return new ResponseEntity<>("Failed to get users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getUserById(UUID id) {
        try {
            Optional<Users> user = usersRepository.findById(id);
            if (user.isPresent()) {
                log.info("User: {}", user.get());
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                log.warn("User not found");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to get user", e);
            return new ResponseEntity<>("Failed to get user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> saveUser(Users user) {
        try {
            Users savedUser = usersRepository.save(user);
            log.info("User saved: {}", savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Handle case when username or email already exists
            log.error("Username or email already exists", e);
            return new ResponseEntity<>("Username or email already exists", HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.error("Failed to save user", e);
            return new ResponseEntity<>("Failed to save user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<?> deleteUser(UUID id) {
        try {
            usersRepository.deleteById(id);
            log.info("User deleted: {}", id);
            return ResponseEntity.ok().build(); // Jika pengguna berhasil dihapus
        } catch (EmptyResultDataAccessException e) {
            // Handle case when the user with the specified ID is not found
            log.warn("User not found");
            return ResponseEntity.notFound().build(); // Jika pengguna tidak ditemukan
        } catch (Exception e) {
            // Handle other unexpected errors
            log.error("Failed to delete user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user"); // Jika terjadi kesalahan internal server
        }
    }
}

