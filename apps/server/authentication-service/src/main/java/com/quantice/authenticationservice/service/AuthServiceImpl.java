package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import com.quantice.authenticationservice.model.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Override
    public SignInRequest signIn(final SignInRequest signInRequest) {

        return null;
    }

    @Override
    public SignUpRequest signUp(final SignUpRequest signUpRequest) {

        return null;
    }

    @Override
    public OAuth2Response signInOAuth2(final OAuth2AuthenticationToken authentication) {

        return null;
    }
}
