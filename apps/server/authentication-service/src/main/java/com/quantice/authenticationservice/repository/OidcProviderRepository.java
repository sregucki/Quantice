package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.OidcProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OidcProviderRepository extends JpaRepository<OidcProvider, Integer> {

    OidcProvider findOidcProviderByOidcProviderName(String oidcProviderName);
}
