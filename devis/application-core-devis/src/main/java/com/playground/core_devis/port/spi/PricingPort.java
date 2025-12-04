package com.playground.core_devis.port.spi;

import com.playground.core_devis.domain.model.Pricing;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;

public interface PricingPort {
    Pricing getTarif(ProductType type, Profil profil);
}