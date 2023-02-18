package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthProvider;
import com.quantice.authenticationservice.repository.AuthProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProviderServiceImpl implements AuthProviderService {
	
	private final AuthProviderRepository authProviderRepository;
	
	@Override
	public AuthProvider findAuthProviderByAuthProviderName(final String authProviderName) {
		
		return authProviderRepository.findAuthProviderByAuthProviderName(authProviderName);
	}
}
