package com.quantice.usermanagementservice.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

    @Column(name = "role_id_fk")
    private int userRoleIdFk;

    @Builder.Default()
    @OneToMany
    @JoinColumn(name = "role_id", referencedColumnName = "role_id_fk")
    private List<Role> roles = new ArrayList<>();

    @Builder.Default()
    @Column(name = "user_is_active")
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "user_modified_at", nullable = false)
    private LocalDateTime modifiedAt;

}
