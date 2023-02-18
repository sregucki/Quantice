package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Integer> {

    AuthProvider findAuthProviderByAuthProviderName(String authProviderName);
}
