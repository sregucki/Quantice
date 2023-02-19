package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.Provider;

public interface ProviderService {
	
	Provider findAuthProviderByAuthProviderName(String authProviderName);
}
