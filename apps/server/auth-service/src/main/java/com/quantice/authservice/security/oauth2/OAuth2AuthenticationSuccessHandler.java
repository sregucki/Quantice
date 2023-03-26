package com.quantice.authservice.security.oauth2;

import com.quantice.authservice.security.jwt.TokenProvider;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {

        String token = tokenProvider.createToken(tokenProvider.getNameOAuth2(authentication));
        LOGGER.info(String.format("Token generated successfully: %s", token));

        getRedirectStrategy().sendRedirect(request, response, "http://localhost:4201/oauth2/%s".formatted(token));
    }
}
