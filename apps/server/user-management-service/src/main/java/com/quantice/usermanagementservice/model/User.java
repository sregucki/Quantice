package com.quantice.usermanagementservice.model;

import com.quantice.usermanagementservice.model.converter.UserRoleListConverter;
import com.quantice.usermanagementservice.model.enums.AuthProvider;
import com.quantice.usermanagementservice.model.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "user_data")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private UUID userId;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "user_auth_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Builder.Default()
    @Convert(converter = UserRoleListConverter.class)
    @Column(name = "user_role", nullable = false)
    private List<UserRole> roles = new ArrayList<>(List.of(UserRole.ROLE_USER));

    @Builder.Default()
    @Column(name = "user_is_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "user_modified_at", nullable = false)
    private LocalDateTime modifiedAt;

}
