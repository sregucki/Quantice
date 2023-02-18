package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.AuthToken;
import com.quantice.authenticationservice.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    
    @Override
    public AuthToken saveIfNotExists(final AuthToken authToken) {
        
        if (authTokenRepository.existsAuthTokenByAccessToken(authToken.getAccessToken())) {
            return authToken;
        }
        return authTokenRepository.save(authToken);
    }
    
}
