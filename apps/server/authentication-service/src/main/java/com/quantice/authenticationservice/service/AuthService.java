package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.Token;
import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface AuthService {

    Token signIn(final SignInRequest signInRequest);

    void signUp(final SignUpRequest signUpRequest);

    Token signInOAuth2(final OAuth2AuthenticationToken authentication);
}
