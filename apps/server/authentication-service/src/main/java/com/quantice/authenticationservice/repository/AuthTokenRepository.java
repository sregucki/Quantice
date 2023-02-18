package com.quantice.authenticationservice.repository;

import com.quantice.authenticationservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<Token, Long> {
	
	boolean existsAuthTokenByAccessToken(String accessToken);
}
