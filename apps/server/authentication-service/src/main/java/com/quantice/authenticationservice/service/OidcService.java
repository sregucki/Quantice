package com.quantice.authenticationservice.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

public interface OidcService {

    OidcIdToken getOidcToken(final OAuth2AuthenticationToken authentication, final OidcIdToken idToken);
}
