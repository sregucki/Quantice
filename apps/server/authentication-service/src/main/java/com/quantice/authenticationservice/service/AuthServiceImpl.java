package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.exception.AuthenticationServiceException;
import com.quantice.authenticationservice.model.AuthEntity;
import com.quantice.authenticationservice.model.Token;
import com.quantice.authenticationservice.model.request.SignInRequest;
import com.quantice.authenticationservice.model.request.SignUpRequest;
import com.quantice.authenticationservice.model.response.OAuth2Response;
import com.quantice.authenticationservice.security.PasswordEncoder;
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
public class AuthServiceImpl implements AuthService {
	
	private final OAuth2AuthorizedClientService authorizedClientService;
	private final AuthEntityService authEntityService;
	private final ProviderService providerService;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	public static final String DEFAULT_AUTH_PROVIDER = "quantice";
	
	@Override
	public Token signIn(final SignInRequest signInRequest) {
		
		return Token.builder().build();
	}
	
	@Override
	public void signUp(final SignUpRequest signUpRequest) {
		
		// TODO sqs message to user-management service
		authEntityService.saveIfNotExists(
				AuthEntity.builder()
						  .email(signUpRequest.getEmail())
						  .password(passwordEncoder.bCryptPasswordEncoder().encode(signUpRequest.getPassword()))
						  .provider(providerService.findAuthProviderByAuthProviderName(DEFAULT_AUTH_PROVIDER))
						  .build()
		);
	}
	
	@Override
	public Token signInOAuth2(final OAuth2AuthenticationToken authentication) {
		
		OAuth2Response oAuth2Response = retrieveUserDetailsFromOAuth2Client(authentication);
		authEntityService.saveIfNotExists(
				AuthEntity.builder()
						  .email(oAuth2Response.getEmail())
						  .provider(providerService.findAuthProviderByAuthProviderName(oAuth2Response.getClientId()))
						  .build());
		OAuth2AccessToken oAuth2AccessToken = oAuth2Response.getToken();
		
		return tokenService.saveIfNotExists(
				Token.builder()
					 .accessToken(oAuth2AccessToken.getTokenValue())
					 .tokenIssuedAt(oAuth2AccessToken.getIssuedAt())
					 .tokenExpiresAt(oAuth2AccessToken.getExpiresAt())
					 .authEntity(authEntityService.findAuthEntityByEmail(oAuth2Response.getEmail()).get())
					 .build());
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
		
		return OAuth2Response
				.builder()
				.username(String.valueOf(userAttributes.get("name")))
				.email(String.valueOf(userAttributes.get("email")))
				.token(client.getAccessToken())
				.avatarUrl(String.valueOf(userAttributes.get("picture")))
				.clientId(authenticationToken.getAuthorizedClientRegistrationId())
				.build();
	}
}
