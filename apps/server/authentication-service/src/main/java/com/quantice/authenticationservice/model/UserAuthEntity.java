package com.quantice.authenticationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "user_login_data")
public class UserAuthEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID userId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "auth_provider_id", referencedColumnName = "id", nullable = false)
    private OidcProvider oidcProvider;
}
