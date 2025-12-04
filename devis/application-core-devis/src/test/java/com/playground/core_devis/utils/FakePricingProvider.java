package com.playground.core_devis.utils;

import com.playground.core_devis.port.spi.PricingConfigProvider;

import java.math.BigDecimal;

import static com.playground.core_devis.utils.TestConstantDevis.DEFAULT_TARIF;

public class FakePricingProvider implements PricingConfigProvider {

    @Override
    public BigDecimal getDefaultPrice() {
        return DEFAULT_TARIF;
    }
}
