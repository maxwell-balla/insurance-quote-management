package com.playground.adapter_pricing;

import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profile;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class PriceRepositoryMongoAdapter implements PricingRepositoryPort {

    @Override
    public Optional<BigDecimal> getTarif(ProductType productType, Profile profile) {
        return Optional.of(BigDecimal.valueOf(300));
    }
}
