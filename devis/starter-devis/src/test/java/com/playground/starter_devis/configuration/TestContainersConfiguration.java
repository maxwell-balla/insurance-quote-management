package com.playground.starter_devis.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    private static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.4.5";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final GenericContainer<?> keycloakContainer;

    @Bean
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:18-alpine")
        );
    }

    static {
        // --- Keycloak ---
        keycloakContainer = new GenericContainer<>(DockerImageName.parse(KEYCLOAK_IMAGE))
                .withExposedPorts(8080)
                .withEnv("KEYCLOAK_ADMIN", ADMIN_USERNAME)
                .withEnv("KEYCLOAK_ADMIN_PASSWORD", ADMIN_PASSWORD)
                .withClasspathResourceMapping(
                        "keycloak/realm-export.json",
                        "/opt/keycloak/data/import/realm-export.json",
                        BindMode.READ_ONLY
                )
                .withEnv("KC_HTTP_RELATIVE_PATH", "/")
                .withEnv("KC_HEALTH_ENABLED", "true")
                .withCommand("start-dev", "--import-realm")
                .waitingFor(Wait.forLogMessage(".*Listening on.*", 1)
                        .withStartupTimeout(Duration.ofMinutes(2)))
                .withReuse(true);

        keycloakContainer.start();
    }

    @Bean
    public static GenericContainer<?> getKeycloakContainer() {
        return keycloakContainer;
    }
}