package com.quantice.insight.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.quantice.insight.repository")
@Configuration
@RequiredArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final MongoProperties mongoProperties;

    @NonNull
    @Bean
    public MongoClient mongoClient() {

        ConnectionString connectionString = new ConnectionString("""
                mongodb://%s:%s/%s
                """.formatted(mongoProperties.getHostname(), mongoProperties.getPort(), mongoProperties.getDatabaseName()));

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @NonNull
    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabaseName();
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

}
