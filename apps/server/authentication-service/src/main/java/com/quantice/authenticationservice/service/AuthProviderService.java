package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthProvider;

public interface AuthProviderService {
	
	AuthProvider findAuthProviderByAuthProviderName(String authProviderName);
}
