package com.quantice.usermanagementservice.model.dto;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class SignUpRequest {

    String username;
    String email;
    String password;

}
