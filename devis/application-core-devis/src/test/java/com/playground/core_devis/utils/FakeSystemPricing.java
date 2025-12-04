package com.playground.core_devis.utils;

import com.playground.core_devis.domain.model.Pricing;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.port.spi.PricingPort;

import static com.playground.core_devis.utils.TestConstantDevis.DEFAULT_TARIF;
import static com.playground.core_devis.utils.TestConstantDevis.TARIF;

public class FakeSystemPricing implements PricingPort {

    private boolean shouldFallback = false;

    public void enableFallback() {
        this.shouldFallback = true;
    }

    @Override
    public Pricing getTarif(ProductType type, Profil profil) {
        if (shouldFallback) {
            return new Pricing(DEFAULT_TARIF);
        }
        return new Pricing(TARIF);
    }
}