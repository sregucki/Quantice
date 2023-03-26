package com.quantice.authservice.controller;

import com.quantice.authservice.security.jwt.TokenValidator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenValidator tokenValidator;


    @PostMapping(value = "/token/validate", consumes = { "application/json" })
    public ResponseEntity<Boolean> validateToken(@RequestBody Map<String, String> request) {
        return new ResponseEntity<>(tokenValidator.validateToken(request.get("token")), HttpStatus.OK);
    }

}
