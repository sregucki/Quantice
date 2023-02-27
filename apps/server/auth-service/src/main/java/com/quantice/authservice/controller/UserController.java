package com.quantice.authservice.controller;

import com.quantice.authservice.model.User;
import com.quantice.authservice.model.dto.LoginRequest;
import com.quantice.authservice.model.dto.RegisterRequest;
import com.quantice.authservice.model.enums.AuthProvider;
import com.quantice.authservice.security.PasswordEncoder;
import com.quantice.authservice.security.jwt.TokenProvider;
import com.quantice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    // TODO @Valid on request bodies
    // TODO exception handling
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
                )
        );

        String token = tokenProvider.createToken(tokenProvider.getName(authentication));

        LOGGER.info(String.format("Token for logging user: %s", token));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);

        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest) {

        // TODO throw exception if user with email exists

        Optional<User> user = userService.saveIfNotExists(
                User.builder()
                        .name(registerRequest.getUsername())
                        .email(registerRequest.getEmail())
                        .passwordHash(passwordEncoder.bCryptPasswordEncoder().encode(registerRequest.getPassword()))
                        .authProvider(AuthProvider.QUANTICE)
                        .build()
        );

        if (user.isEmpty()) {

            LOGGER.error("Error while processing register request");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        LOGGER.info(String.format("Registering user: %s", user.get()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
