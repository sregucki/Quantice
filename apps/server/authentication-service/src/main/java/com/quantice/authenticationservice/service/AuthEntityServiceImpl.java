package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthEntity;
import com.quantice.authenticationservice.repository.AuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthEntityServiceImpl implements AuthEntityService {
	
	private final AuthEntityRepository authEntityRepository;
	
	@Override
	public AuthEntity saveIfNotExists(final AuthEntity authEntity) {
		
		if (authEntityRepository.existsAuthEntityByEmail(authEntity.getEmail())) {
			return authEntity;
		}
		return authEntityRepository.save(authEntity);
	}
	
	@Override
	public Optional<AuthEntity> findAuthEntityByEmail(final String email) {
		
		return authEntityRepository.findAuthEntityByEmail(email);
	}
	
}
