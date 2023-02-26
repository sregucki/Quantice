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

        if (!userRepository.existsByEmail(user.getEmail())) {

            return Optional.of(userRepository.save(user));
        }

        return Optional.empty();
    }
}
