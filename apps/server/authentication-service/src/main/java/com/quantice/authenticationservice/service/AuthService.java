package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import com.quantice.authenticationservice.model.response.OAuth2Response;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface AuthService {

    SignInRequest signIn(final SignInRequest signInRequest);

    SignUpRequest signUp(final SignUpRequest signUpRequest);

    OAuth2Response signInOAuth2(final OAuth2AuthenticationToken authentication);

}
