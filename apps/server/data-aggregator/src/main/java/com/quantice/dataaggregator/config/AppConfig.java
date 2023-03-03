package com.quantice.dataaggregator.config;

import com.rometools.rome.io.SyndFeedInput;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public SyndFeedInput syndFeedInput() {
        return new SyndFeedInput();
    }

}
