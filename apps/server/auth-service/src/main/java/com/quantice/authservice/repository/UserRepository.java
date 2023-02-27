package com.quantice.authservice.repository;

import com.quantice.authservice.model.User;
import com.quantice.authservice.model.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailAndAuthProvider(String email, AuthProvider authProvider);

    boolean existsByEmailAndAuthProviderNot(String email, AuthProvider authProvider);

    Optional<User> findUserById(UUID id);

    boolean existsByEmail(String email);

}
