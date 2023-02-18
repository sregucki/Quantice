package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public interface AuthService {

    SignInRequest signIn(final SignInRequest signInRequest);

    SignUpRequest signUp(final SignUpRequest signUpRequest);

    OAuth2AccessToken signInOAuth2(final OAuth2AuthenticationToken authentication);

}
