package com.quantice.authservice.security.oauth2;

import com.quantice.authservice.model.User;
import com.quantice.authservice.model.enums.AuthProvider;
import com.quantice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        String userEmail = String.valueOf(oAuth2User.getAttributes().get("email"));

        if (userEmail.isEmpty()) {

            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        userService.saveIfNotExists(User.builder()
                        .email(userEmail)
                        .authProvider(AuthProvider.valueOfIgnoreCase(oAuth2UserRequest.getClientRegistration().getClientName()))
                .build()).orElseThrow(() -> new OAuth2AuthenticationException(String.format("User with email %s already exists", userEmail)));

        // TODO update if user change image, name etc.

        return oAuth2User;
    }


}
