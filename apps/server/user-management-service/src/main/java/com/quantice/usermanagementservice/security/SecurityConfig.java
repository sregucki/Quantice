package com.quantice.usermanagementservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
            .requestMatchers("/login")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login()
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .failureUrl("/login")
            .and()
            .build();
    }

}
