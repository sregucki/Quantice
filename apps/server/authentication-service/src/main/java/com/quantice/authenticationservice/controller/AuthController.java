package com.quantice.authenticationservice.controller;

import com.quantice.authenticationservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/home")
    public String home(OAuth2AuthenticationToken authentication) {

        return authService.signInOAuth2(authentication).toString();
    }

}
