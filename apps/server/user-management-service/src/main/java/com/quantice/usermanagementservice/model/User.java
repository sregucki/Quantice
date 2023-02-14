package com.quantice.usermanagementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "user_data")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private UUID userId;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_password_hash")
    private String passwordHash;

}
