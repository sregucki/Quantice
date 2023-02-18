package com.quantice.authenticationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private AuthProvider authProvider;

}
