package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.Provider;
import com.quantice.authenticationservice.repository.AuthProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
	
	private final AuthProviderRepository authProviderRepository;
	
	@Override
	public Provider findAuthProviderByAuthProviderName(final String authProviderName) {
		
		return authProviderRepository.findAuthProviderByAuthProviderName(authProviderName);
	}
}
