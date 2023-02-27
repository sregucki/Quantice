package com.quantice.authservice.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterRequest {

    private String username;
    private String password;
    private String email;

}
