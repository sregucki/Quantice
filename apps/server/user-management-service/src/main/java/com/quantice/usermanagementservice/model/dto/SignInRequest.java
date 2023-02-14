package com.quantice.usermanagementservice.model.dto;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class SignInRequest {

    String email;
    String password;

}
