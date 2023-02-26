package com.quantice.authservice.config;

import com.quantice.authservice.security.jwt.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = TokenProperties.class)
public class AppConfig {

}
