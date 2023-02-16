package com.quantice.usermanagementservice.service;

import com.quantice.usermanagementservice.model.User;
import com.quantice.usermanagementservice.model.dto.SignInRequest;
import com.quantice.usermanagementservice.model.dto.SignUpRequest;
import com.quantice.usermanagementservice.repository.UserRepository;
import com.quantice.usermanagementservice.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // TODO authentication manager here
    public void signIn(SignInRequest signInRequest) {

    }

    public void signUp(SignUpRequest signUpRequest) {

        userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .build()
        );
    }

}
