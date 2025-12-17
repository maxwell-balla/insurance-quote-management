package com.playground.inproc.service;

import com.playground.core_devis.domain.model.Pricing;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.port.spi.PricingConfigProvider;
import com.playground.core_devis.port.spi.PricingPort;
import com.playground.core_devis.port.spi.TokenProviderPort;
import com.playground.inproc.PricingHttpClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingServiceRestAdapter implements PricingPort {

    private static final String PRICING_SERVICE = "pricingService";
    private final Logger log = LoggerFactory.getLogger(PricingServiceRestAdapter.class);

    private final PricingHttpClient pricingHttpClient;
    private final PricingConfigProvider pricingConfigProvider;
    private final TokenProviderPort tokenProviderPort;

    public PricingServiceRestAdapter(
            PricingHttpClient pricingHttpClient,
            PricingConfigProvider pricingConfigProvider,
            TokenProviderPort tokenProviderPort) {
        this.pricingHttpClient = pricingHttpClient;
        this.pricingConfigProvider = pricingConfigProvider;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    @CircuitBreaker(name = PRICING_SERVICE)
    @Retry(name = PRICING_SERVICE, fallbackMethod = "fallbackTarif")
    public Pricing getTarif(ProductType type, Profil profil) {
        log.info("[PRICING] Calling pricing service | product={}, age={}", type, profil.age());
        String token = tokenProviderPort.getAccessToken();
        String bearer = "Bearer " + token;
        Pricing pricing = pricingHttpClient.getTarif(type, profil.age(), bearer);
        log.info("[PRICING] Success | price={}", pricing.tarif());
        return pricing;
    }

    private Pricing fallbackTarif(Throwable throwable) {
        BigDecimal defaultPrice = pricingConfigProvider.getDefaultPrice();
        log.warn("[FALLBACK] Error: {} | Returning default price: {}", throwable.getMessage(), defaultPrice);
        return new Pricing(defaultPrice);
    }
}
