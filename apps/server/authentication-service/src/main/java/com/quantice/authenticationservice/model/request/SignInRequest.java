package com.quantice.authenticationservice.model.request;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class SignInRequest {

    String email;
    String password;

}
