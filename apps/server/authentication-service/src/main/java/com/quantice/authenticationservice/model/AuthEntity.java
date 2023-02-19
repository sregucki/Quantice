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
public class AuthEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private UUID userId;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password_hash")
    private String password;

    @OneToOne
    @JoinColumn(name = "auth_provider_id", referencedColumnName = "auth_provider_id", nullable = false)
    private Provider provider;
}
