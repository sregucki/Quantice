package com.quantice.dataaggregator.config;

import com.rometools.rome.io.SyndFeedInput;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ConfigurationPropertiesScan(basePackageClasses = RssProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public SyndFeedInput syndFeedInput() {
        return new SyndFeedInput();
    }

}
