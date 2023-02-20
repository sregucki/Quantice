package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.OidcProvider;

public interface OidcProviderService {
	
	OidcProvider findAuthProviderByAuthProviderName(String authProviderName);
}
