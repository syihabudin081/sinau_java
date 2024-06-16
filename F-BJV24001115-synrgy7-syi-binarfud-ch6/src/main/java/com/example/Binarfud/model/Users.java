package com.example.Binarfud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
@SoftDelete
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "verified", nullable = false)
    private boolean verified = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotEmpty(message = "Roles cannot be empty")
    @NonNull
    private Set<Role> roles = new HashSet<>();


    public Users( String username, String emailAddress, String password, Set<Role> roles) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles = roles;
    }

}
