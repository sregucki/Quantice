package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.UserAuthEntity;

import java.util.Optional;

public interface UserAuthEntityService {
	
	UserAuthEntity saveIfNotExists(UserAuthEntity userAuthEntity);
	
	Optional<UserAuthEntity> findUserAuthEntityByEmail(String email);
}
