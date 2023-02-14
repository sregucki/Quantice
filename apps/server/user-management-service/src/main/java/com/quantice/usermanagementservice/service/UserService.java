package com.quantice.usermanagementservice.service;

import com.quantice.usermanagementservice.model.User;
import com.quantice.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {

        return userRepository.save(user);

    }

}
