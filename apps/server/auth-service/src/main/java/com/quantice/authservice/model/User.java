package com.quantice.authservice.model;

import com.quantice.authservice.model.enums.AuthProvider;
import com.quantice.authservice.model.enums.Role;
import com.quantice.authservice.util.UserRoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "user_auth_data")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "username", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "auth_provider", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AuthProvider authProvider;

    @Builder.Default()
    @Column(name = "user_role", nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private List<Role> roles = new ArrayList<>(List.of(Role.ROLE_USER));

}
