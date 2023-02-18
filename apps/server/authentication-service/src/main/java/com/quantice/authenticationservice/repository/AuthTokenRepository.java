package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
	
	boolean existsAuthTokenByAccessToken(String accessToken);
}
