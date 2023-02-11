package com.quantice.authenticationservice.config.database.postgres;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Value
@ConfigurationProperties(prefix = "postgres")
public class PostgresProperties {

    String host;
    String port;
    String databaseName;
    String username;
    String password;

}
