package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthEntity;

import java.util.Optional;

public interface AuthEntityService {
	
	AuthEntity saveIfNotExists(AuthEntity authEntity);
	
	Optional<AuthEntity> findAuthEntityByEmail(String email);
}
