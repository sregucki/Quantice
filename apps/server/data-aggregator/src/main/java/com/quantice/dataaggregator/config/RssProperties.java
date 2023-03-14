package com.quantice.dataaggregator.config;

import com.quantice.dataaggregator.model.Language;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConfigurationProperties(prefix = "rss")
@ConstructorBinding
public class RssProperties {

    Language language;

    String channelsFileName;

}
