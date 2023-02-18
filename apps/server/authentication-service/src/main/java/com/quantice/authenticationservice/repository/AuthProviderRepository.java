package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<Provider, Integer> {

    Provider findAuthProviderByAuthProviderName(String authProviderName);
}
