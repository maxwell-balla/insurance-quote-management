package com.playground.core_pricing.port.spi;

import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profile;

import java.math.BigDecimal;
import java.util.Optional;

public interface PricingRepositoryPort {
    Optional<BigDecimal> getTarif(ProductType productType, Profile profile);
}
