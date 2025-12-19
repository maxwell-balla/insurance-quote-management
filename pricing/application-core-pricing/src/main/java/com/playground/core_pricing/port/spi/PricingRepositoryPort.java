package com.playground.core_pricing.port.spi;

import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profil;
import com.playground.core_pricing.domain.model.TarifQuoteView;

import java.util.Optional;

public interface PricingRepositoryPort {
    Optional<TarifQuoteView> getTarif(ProductType productType, Profil profil);
}
