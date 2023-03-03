package com.quantice.dataaggregator.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = MongoProperties.class)
@RequiredArgsConstructor
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private final MongoProperties mongoProperties;

    @Bean
    @NonNull
    public MongoClient reactiveMongoClient() {

        ConnectionString connectionString = new ConnectionString("""
                mongodb://%s:%s/%s
                """.formatted(mongoProperties.getHostname(), mongoProperties.getPort(), mongoProperties.getDatabaseName()));

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return mongoProperties.getDatabaseName();
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
