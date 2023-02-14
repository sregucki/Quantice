package com.quantice.usermanagementservice.controller;

import com.quantice.usermanagementservice.model.User;
import com.quantice.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        userService.register(user);

        return user.toString();
    }

}
