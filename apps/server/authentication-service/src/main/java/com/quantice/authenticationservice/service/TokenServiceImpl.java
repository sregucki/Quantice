package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.Token;
import com.quantice.authenticationservice.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final AuthTokenRepository authTokenRepository;
    
    @Override
    public Token saveIfNotExists(final Token token) {
        
        if (authTokenRepository.existsAuthTokenByAccessToken(token.getAccessToken())) {
            return token;
        }
        return authTokenRepository.save(token);
    }
    
}
