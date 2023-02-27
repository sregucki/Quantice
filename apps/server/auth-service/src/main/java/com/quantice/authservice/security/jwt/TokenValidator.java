package com.quantice.authservice.security.jwt;

import com.quantice.authservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenValidator {

    private SecretKey secretKey;
    private final TokenProperties tokenProperties;
    private final UserRepository userRepository;

    @PostConstruct
    public void initSecretKey() {

        String secret = Base64.getEncoder().encodeToString(tokenProperties.getTokenSecret().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {

        return userRepository.existsByEmail(getClaimFromToken(token, Claims::getSubject)) && isExpired(token);
    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    private boolean isExpired(String token) {

        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

}
