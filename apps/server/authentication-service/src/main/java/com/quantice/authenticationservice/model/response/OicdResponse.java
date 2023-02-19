package com.quantice.authenticationservice.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

@Builder
@Getter
@ToString
public class OicdResponse {

    private String username;
    private String email;
    private OAuth2AccessToken token;
    private String avatarUrl;
    private String clientId;
}
