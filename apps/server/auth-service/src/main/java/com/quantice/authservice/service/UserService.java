package com.quantice.authservice.service;

import com.quantice.authservice.model.User;
import com.quantice.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> saveIfNotExists(User user) {

        if (userRepository.existsByEmailAndAuthProviderNot(user.getEmail(), user.getAuthProvider())) {

            return Optional.empty();
        }

        if (!userRepository.existsByEmailAndAuthProvider(user.getEmail(), user.getAuthProvider())) {

            return Optional.of(userRepository.save(user));
        }

        // TODO Update here

        return Optional.of(user);
    }
}
