package com.quantice.authservice.repository;

import com.quantice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserId(UUID id);

}
