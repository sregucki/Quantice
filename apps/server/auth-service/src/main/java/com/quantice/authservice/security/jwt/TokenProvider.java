package com.quantice.authservice.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private SecretKey secretKey;
    private final TokenProperties tokenProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    @PostConstruct
    public void initSecretKey() {

        String secret = Base64.getEncoder().encodeToString(tokenProperties.getTokenSecret().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getName(Authentication authentication) {

        return authentication.getName();
    }

    public String getNameOAuth2(Authentication authentication) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return String.valueOf(oAuth2User.getAttributes().get("email"));
    }

    public String createToken(String name) {

        Instant instant = Instant.now().plus(tokenProperties.getTokenExpirationMsec(), ChronoUnit.MILLIS);
        Date expiryDate = Date.from(instant);

        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
