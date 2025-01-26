package com.inditex.asset;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * This is the main class
 */
@SpringBootApplication(scanBasePackages = {"com.inditex.asset"})
@ConfigurationPropertiesScan({"com.inditex.asset.infraestructure.config"})
@EnableAutoConfiguration
@EnableR2dbcRepositories
@EnableWebFlux
public class AssetApplication {

    /**
     * Initializer connection factory initializer.
     *
     * @param connectionFactory the connection factory
     * @return the connection factory initializer
     */
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    /**
     * Main method in the spring boot app
     *
     * @param args parameter of the class
     */
    public static void main(String[] args) {
        SpringApplication.run(AssetApplication.class, args);
    }
}
