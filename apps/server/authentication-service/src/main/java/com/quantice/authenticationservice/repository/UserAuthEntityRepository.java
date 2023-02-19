package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthEntityRepository extends JpaRepository<UserAuthEntity, String> {

    boolean existsUserAuthEntityByEmail(String email);
    
    Optional<UserAuthEntity> findUserAuthEntityByEmail(String email);
}
