package com.quantice.authenticationservice.config.database;

import com.quantice.authenticationservice.config.database.postgres.PostgresProperties;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class DatabaseConfigImpl implements DatabaseConfig {

    private final PostgresProperties postgresProperties;

    @Override
    @Bean
    public ConnectionFactoryBuilder connectionFactoryBuilder() {

        return ConnectionFactoryBuilder.withOptions(
            ConnectionFactoryOptions
                .builder()
                    .option(ConnectionFactoryOptions.HOST, postgresProperties.getHost())
                    .option(ConnectionFactoryOptions.PORT, Integer.parseInt(postgresProperties.getPort()))
                    .option(ConnectionFactoryOptions.USER, postgresProperties.getUsername())
                    .option(ConnectionFactoryOptions.PASSWORD, postgresProperties.getPassword())
                    .option(ConnectionFactoryOptions.DATABASE, postgresProperties.getDatabaseName())
        );
    }
}
