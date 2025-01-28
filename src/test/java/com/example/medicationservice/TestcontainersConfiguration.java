package com.example.medicationservice;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Profile("test")
@ContextConfiguration
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> postgresContainer =
           new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName("healthsmartdb_medication")
                .withUsername("user")
                .withPassword("password");

        postgresContainer.start();
        return postgresContainer;
    }
}
