package com.playground.adapter_devis;

import com.playground.core_devis.port.spi.PricingConfigProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PricingProviderSpringAdapter implements PricingConfigProvider {

    private final BigDecimal defaultPrice;

    public PricingProviderSpringAdapter(
            @Value("${quote.pricing.default-price}") BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    @Override
    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }
}
