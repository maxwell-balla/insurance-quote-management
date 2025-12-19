package com.playground.core_pricing.utils;

import com.playground.core_pricing.domain.model.Price;
import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profil;
import com.playground.core_pricing.domain.model.TarifQuoteView;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.playground.core_pricing.utils.TestConstantPricing.*;

public class FakePricingRepository implements PricingRepositoryPort {

    private final Map<UUID, Price> priceByIds = new HashMap<>();

    @Override
    public Optional<TarifQuoteView> getTarif(ProductType productType, Profil profil) {
        return priceByIds.values().stream()
                .filter(price -> price.getProductType().equals(productType))
                .filter(price -> price.getProfil().equals(profil))
                .findFirst()
                .map(price -> new TarifQuoteView(price.getTarif()));
    }

    public void feedPrice() {
        var price = new Price(PRICE_ID, TARIF, ProductType.AUTO, new Profil(AGE));
        priceByIds.put(PRICE_ID, price);
    }

    public void feedPriceWithAge(int age) {
        var priceId = UUID.randomUUID();
        var price = new Price(priceId, TARIF, ProductType.AUTO, new Profil(age));
        priceByIds.put(priceId, price);
    }
}
