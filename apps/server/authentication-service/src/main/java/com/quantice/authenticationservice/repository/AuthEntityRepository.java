package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthEntityRepository extends JpaRepository<AuthEntity, String> {

    boolean existsAuthEntityByEmail(String email);
    
    AuthEntity findAuthEntityByEmail(String email);
}
