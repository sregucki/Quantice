package com.quantice.usermanagementservice.model.auth;

import com.quantice.usermanagementservice.model.user.User;
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

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "token_data")
public class Token {

    @Id
    @Column(name = "token_id")
    @GeneratedValue
    private UUID tokenId;

    @Column(name = "access_token", nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "token_issued_at", nullable = false)
    private LocalDateTime tokenIssuedAt;

    @Column(name = "token_expires_at", nullable = false)
    private LocalDateTime tokenExpiresAt;

    @OneToOne
    @JoinColumn(name = "auth_provider_id_fk", referencedColumnName = "auth_provider_id", nullable = false)
    private AuthProvider authProvider;

    @OneToOne
    @JoinColumn(name = "user_id_fk", referencedColumnName = "user_id", nullable = false)
    private User user;

}
