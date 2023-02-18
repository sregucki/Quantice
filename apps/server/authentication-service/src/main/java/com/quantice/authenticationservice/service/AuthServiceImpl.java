package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.exception.AuthenticationServiceException;
import com.quantice.authenticationservice.model.AuthEntity;
import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import com.quantice.authenticationservice.model.response.OAuth2Response;
import com.quantice.authenticationservice.repository.AuthEntityRepository;
import com.quantice.authenticationservice.repository.AuthProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final AuthEntityRepository authEntityRepository;
    private final AuthProviderRepository authProviderRepository;


    @Override
    public SignInRequest signIn(final SignInRequest signInRequest) {

        return null;
    }

    @Override
    public SignUpRequest signUp(final SignUpRequest signUpRequest) {

        return null;
    }

    @Override
    public OAuth2AccessToken signInOAuth2(final OAuth2AuthenticationToken authentication) {

        OAuth2Response oAuth2Response = retrieveUserDetailsFromOAuth2Client(authentication);
        saveAuthEntityIfNotExists(AuthEntity.builder()
            .email(oAuth2Response.getEmail())
            .authProvider(authProviderRepository
                .findAuthProviderByAuthProviderName(oAuth2Response.getClientId()))
            .build());

        return oAuth2Response.getToken();
    }

    private void saveAuthEntityIfNotExists(AuthEntity authEntity) {

        if (!authEntityRepository.existsAuthEntityByEmail(authEntity.getEmail())) {
            authEntityRepository.save(authEntity);
        }
    }

    public OAuth2Response retrieveUserDetailsFromOAuth2Client(final OAuth2AuthenticationToken authenticationToken) {

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            authenticationToken.getAuthorizedClientRegistrationId(),
            authenticationToken.getName()
        );

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (userInfoEndpointUri.isEmpty()) {
            throw new AuthenticationServiceException();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                .getTokenValue());

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
        Map userAttributes = response.getBody();

        return OAuth2Response.builder()
            .username(String.valueOf(userAttributes.get("name")))
            .email(String.valueOf(userAttributes.get("email")))
            .token(client.getAccessToken())
            .avatarUrl(String.valueOf(userAttributes.get("picture")))
            .clientId(authenticationToken.getAuthorizedClientRegistrationId())
            .build();
    }

}
