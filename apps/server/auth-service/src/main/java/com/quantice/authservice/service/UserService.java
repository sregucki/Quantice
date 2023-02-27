package com.quantice.authservice.service;

import com.quantice.authservice.model.User;
import com.quantice.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with email: %s not found", username)));
    }

}
