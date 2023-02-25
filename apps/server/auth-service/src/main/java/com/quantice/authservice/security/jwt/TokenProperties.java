package com.quantice.authservice.security.jwt;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class TokenProperties {

    String tokenSecret;
    long tokenExpirationMsec;
}
