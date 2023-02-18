package com.quantice.authenticationservice.model.request;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class SignUpRequest {

    String username;
    String email;
    String password;

}
