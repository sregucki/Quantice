package com.quantice.authenticationservice.controller;

import com.quantice.authenticationservice.service.OidcServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OidcController {

    private final OidcServiceImpl oidcService;

    @GetMapping("/oidc/token")
    public ResponseEntity<String> getOidcToken(final OAuth2AuthenticationToken authentication,
                                       final @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {

        OidcIdToken token = oidcService.getOidcToken(authentication, idToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", token.getTokenValue()));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
