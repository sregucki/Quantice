package com.quantice.dataaggregator.config.mongo;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConfigurationProperties(prefix = "mongo")
@ConstructorBinding
public class MongoProperties {

    String hostname;
    int port;
    String databaseName;

}
