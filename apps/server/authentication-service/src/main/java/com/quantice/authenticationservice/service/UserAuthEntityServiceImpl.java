package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.UserAuthEntity;
import com.quantice.authenticationservice.repository.UserAuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthEntityServiceImpl implements UserAuthEntityService {
	
	private final UserAuthEntityRepository userAuthEntityRepository;
	
	@Override
	public UserAuthEntity saveIfNotExists(final UserAuthEntity userAuthEntity) {
		
		if (userAuthEntityRepository.existsUserAuthEntityByEmail(userAuthEntity.getEmail())) {
			return userAuthEntity;
		}
		return userAuthEntityRepository.save(userAuthEntity);
	}
	
	@Override
	public Optional<UserAuthEntity> findUserAuthEntityByEmail(final String email) {
		
		return userAuthEntityRepository.findUserAuthEntityByEmail(email);
	}
	
}
