package com.quantice.authenticationservice.config.database;

import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;

public interface DatabaseConfig {

    ConnectionFactoryBuilder connectionFactoryBuilder();

}
