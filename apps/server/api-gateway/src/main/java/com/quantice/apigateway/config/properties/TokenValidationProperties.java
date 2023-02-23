package com.quantice.apigateway.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties(prefix = "token")
public class TokenValidationProperties {

    String authServerUri;
}
