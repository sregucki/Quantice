package com.quantice.authservice.controller;

import com.quantice.authservice.security.jwt.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenValidator tokenValidator;


    @PostMapping("/token/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader String token) {

        return new ResponseEntity<>(tokenValidator.validateToken(token), HttpStatus.OK);
    }

}
