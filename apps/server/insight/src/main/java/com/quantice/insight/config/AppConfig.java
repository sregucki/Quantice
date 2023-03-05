package com.quantice.insight.config;

import com.quantice.insight.config.properties.NewsApiProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = NewsApiProperties.class)
public class AppConfig {

}
