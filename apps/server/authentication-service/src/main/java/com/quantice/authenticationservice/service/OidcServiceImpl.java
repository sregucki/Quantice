package com.quantice.authenticationservice.service;

import com.quantice.authenticationservice.model.UserAuthEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OidcServiceImpl implements OidcService {

	private final UserAuthEntityService userAuthEntityService;
	private final OidcProviderService oidcProviderService;

	@Override
	public OidcIdToken getOidcToken(final OAuth2AuthenticationToken authentication, final OidcIdToken idToken) {

		userAuthEntityService.saveIfNotExists(
				UserAuthEntity.builder()
						  .email(String.valueOf(authentication.getPrincipal().getAttributes().get("email")))
						  .oidcProvider(oidcProviderService.findAuthProviderByAuthProviderName(authentication.getAuthorizedClientRegistrationId()))
						  .build());

		return idToken;
	}
}
