package com.quantice.authenticationservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		return http.authorizeHttpRequests()
				   .requestMatchers("/register", "/auth")
				   .permitAll()
				   .anyRequest()
				   .authenticated()
				   .and()
				   .oauth2Login()
				   .and()
				   .csrf().disable()
				   .build();
	}
}