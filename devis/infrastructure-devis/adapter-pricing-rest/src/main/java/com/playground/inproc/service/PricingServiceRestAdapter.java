package com.playground.inproc.service;

import com.playground.core_devis.domain.model.Pricing;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.port.spi.PricingConfigProvider;
import com.playground.core_devis.port.spi.PricingPort;
import com.playground.core_devis.port.spi.TokenProviderPort;
import com.playground.inproc.PricingHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;

@Service
public class PricingServiceRestAdapter implements PricingPort {

    private final Logger log = LoggerFactory.getLogger(PricingServiceRestAdapter.class);

    private final PricingHttpClient pricingHttpClient;
    private final PricingConfigProvider pricingProvider;
    private final TokenProviderPort tokenProviderPort;

    public PricingServiceRestAdapter(
            PricingHttpClient pricingHttpClient,
            PricingConfigProvider pricingProvider,
            TokenProviderPort tokenProvider) {
        this.pricingHttpClient = pricingHttpClient;
        this.pricingProvider = pricingProvider;
        this.tokenProviderPort = tokenProvider;
    }

    @Override
    @Retryable
    public Pricing getTarif(ProductType type, Profil profil) {
        try {
            log.debug("Construct Bearer Token with Token={}", tokenProviderPort.getAccessToken());
            String token = tokenProviderPort.getAccessToken();
            String bearer = "Bearer " + token;
            log.info("Pricing service calling");
            return pricingHttpClient.getTarif(type, profil.age(), bearer);
        } catch (RestClientException e) {
            log.warn("Pricing service failed, using fallback: {}", e.getMessage());
            return fallbackTarif(type, profil);
        }
    }

    private Pricing fallbackTarif(ProductType type, Profil profil) {
        BigDecimal defaultPrice = pricingProvider.getDefaultPrice();
        log.info("Returning default price");
        return new Pricing(defaultPrice);
    }
}
