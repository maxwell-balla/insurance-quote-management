package com.playground.adapter_devis;

import com.playground.core_devis.port.spi.TokenProviderPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
public class TokenProviderKeycloakAdapter implements TokenProviderPort {

    private final Logger log = LoggerFactory.getLogger(TokenProviderKeycloakAdapter.class);

    private final OAuth2AuthorizedClientManager authClientManger;

    public TokenProviderKeycloakAdapter(OAuth2AuthorizedClientManager authClientManger) {
        this.authClientManger = authClientManger;
    }

    @Override
    public String getAccessToken() {
        log.debug("getAccessToken");
        var authRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak")
                .principal("devis-service")
                .build();
        var authClient = authClientManger.authorize(authRequest);

        if (authClient == null) {
            log.error("OAuth2AuthorizedClientManager returned null");
            throw new IllegalStateException(String.format("No such OAuth2AuthorizedClient: %s", authRequest));
        }

        log.debug("Found OAuth2AuthorizedClient: {}", authClient);
        return authClient.getAccessToken().getTokenValue();
    }
}
