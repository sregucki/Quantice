package com.quantice.authenticationservice.config;

import com.quantice.authenticationservice.config.database.postgres.PostgresProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableConfigurationProperties(PostgresProperties.class)
@EnableWebFlux
public class AppConfig {

}
