package com.quantice.apigateway.config;

import com.quantice.apigateway.config.properties.TokenValidationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = TokenValidationProperties.class)
public class AppConfig {

}
