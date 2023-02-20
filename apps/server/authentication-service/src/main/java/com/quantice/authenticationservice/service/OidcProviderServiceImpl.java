package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.OidcProvider;
import com.quantice.authenticationservice.repository.OidcProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OidcProviderServiceImpl implements OidcProviderService {
	
	private final OidcProviderRepository oidcProviderRepository;
	
	@Override
	public OidcProvider findAuthProviderByAuthProviderName(final String authProviderName) {
		
		return oidcProviderRepository.findOidcProviderByOidcProviderName(authProviderName);
	}
}
