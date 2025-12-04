package com.playground.core_pricing.utils;

import com.playground.core_pricing.domain.model.Price;
import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profile;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.playground.core_pricing.utils.TestConstantPricing.*;

public class FakePricingRepository implements PricingRepositoryPort {

    private final Map<UUID, Price> priceByIds = new HashMap<>();

    @Override
    public Optional<BigDecimal> getTarif(ProductType productType, Profile profile) {
        return priceByIds.values().stream()
                .filter(price -> price.getProductType().equals(productType))
                .filter(price -> price.getProfile().equals(profile))
                .findFirst()
                .map(Price::getTarif);
    }

    public void feedPrice () {
        var price = new Price(PRICE_ID, TARIF, ProductType.AUTO, new Profile(AGE));
        priceByIds.put(PRICE_ID, price);
    }
}
