package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthEntityRepository extends JpaRepository<AuthEntity, String> {

    boolean existsAuthEntityByEmail(String email);
    
    Optional<AuthEntity> findAuthEntityByEmail(String email);
}
