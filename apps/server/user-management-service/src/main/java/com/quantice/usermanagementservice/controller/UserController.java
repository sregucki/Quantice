package com.quantice.usermanagementservice.controller;

import com.quantice.usermanagementservice.model.dto.SignInRequest;
import com.quantice.usermanagementservice.model.dto.SignUpRequest;
import com.quantice.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public String signIn(@RequestBody SignInRequest signInRequest) {

        userService.signIn(signInRequest);
        return String.format("User: %s successfully logged in", signInRequest.getEmail());
    }

    @PostMapping("/register")
    public String signUp(@RequestBody SignUpRequest signUpRequest) {

        userService.signUp(signUpRequest);
        return String.format("User: %s successfully registered", signUpRequest.getUsername());
    }

}
