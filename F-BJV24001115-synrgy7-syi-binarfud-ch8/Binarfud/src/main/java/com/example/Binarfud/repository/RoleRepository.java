package com.example.Binarfud.repository;

import com.example.Binarfud.model.ERole;
import com.example.Binarfud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(ERole name);
}
