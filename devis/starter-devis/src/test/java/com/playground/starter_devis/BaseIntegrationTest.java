package com.playground.starter_devis;

import com.playground.starter_devis.configuration.TestContainersConfiguration;
import com.playground.starter_devis.configuration.WireMockServerConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainersConfiguration.class, WireMockServerConfiguration.class})
public abstract class BaseIntegrationTest {

    private static final String REALM_NAME = "iqmrealm";
    private static final String CLIENT_ID = "devis-service";
    private static final String CLIENT_SECRET = "3JEXplLQJ2fQRBG8UixMtGn5svMGLZFP";
    private static final String GRANT_TYPE = "client_credentials";

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        // Fake Pricing Server
        var wireMockServer = WireMockServerConfiguration.getWireMockServer();
        registry.add("pricing.url", wireMockServer::baseUrl);

        // Fake Keycloak Instance
        var keycloakContainer = TestContainersConfiguration.getKeycloakContainer();
        String keycloakUrl = String.format("http://%s:%d",
                keycloakContainer.getHost(),
                keycloakContainer.getMappedPort(8080));

        registry.add("spring.security.oauth2.client.registration.keycloak.client-id", () -> CLIENT_ID);
        registry.add("spring.security.oauth2.client.registration.keycloak.client-secret", () -> CLIENT_SECRET);
        registry.add("spring.security.oauth2.client.registration.keycloak.authorization-grant-type", () -> GRANT_TYPE);
        registry.add("keycloak.url", () -> keycloakUrl);
        registry.add("spring.security.oauth2.client.provider.keycloak.token-uri",
                () -> keycloakUrl + "/realms/" + REALM_NAME + "/protocol/openid-connect/token");
        registry.add("spring.security.oauth2.client.provider.keycloak.issuer-uri",
                () -> keycloakUrl + "/realms/" + REALM_NAME);
    }
}