package com.quantice.authservice.security.jwt;

import com.quantice.authservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidator.class);

    @PostConstruct
    public void initSecretKey() {

        String secret = Base64.getEncoder().encodeToString(tokenProperties.getTokenSecret().getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {

        LOGGER.info(String.format("Validating token: %s", token));
        String subject;

        try {
            subject = getClaimFromToken(token, Claims::getSubject);
        }
        catch (SignatureException e) {
            LOGGER.error("Invalid token signature");
            return false;
        }
        catch (MalformedJwtException e) {
            LOGGER.error("Invalid jwt token");
            return false;
        }
        catch (ExpiredJwtException e) {
            LOGGER.error("Expired jwt token");
            return false;
        }

        LOGGER.info(String.format("Token subject: %s", subject));
        return userRepository.existsByEmail(getClaimFromToken(token, Claims::getSubject)) && !isExpired(token);
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
