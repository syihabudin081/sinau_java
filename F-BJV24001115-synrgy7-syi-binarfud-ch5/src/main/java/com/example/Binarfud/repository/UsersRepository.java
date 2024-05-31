package com.example.Binarfud.repository;
import com.example.Binarfud.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String email);
}
