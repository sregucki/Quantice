package com.quantice.authenticationservice.controller;

import com.quantice.authenticationservice.service.OidcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final OidcService oidcService;


}
