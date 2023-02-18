package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthEntity;

public interface AuthEntityService {
	
	AuthEntity saveIfNotExists(AuthEntity authEntity);
	
	AuthEntity findAuthEntityByEmail(String email);
}
