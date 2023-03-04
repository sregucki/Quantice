package com.quantice.insight.config.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConfigurationProperties
@ConstructorBinding
public class NewsApiProperties {

    String newsApiKey;

}
