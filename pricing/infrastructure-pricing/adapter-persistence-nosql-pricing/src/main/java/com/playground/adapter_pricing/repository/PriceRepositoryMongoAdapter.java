package com.playground.adapter_pricing.repository;

import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profil;
import com.playground.core_pricing.domain.model.TarifQuoteView;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PriceRepositoryMongoAdapter implements PricingRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(PriceRepositoryMongoAdapter.class);

    private final SpringDataPriceRepository priceRepository;

    public PriceRepositoryMongoAdapter(SpringDataPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<TarifQuoteView> getTarif(ProductType productType, Profil profil) {
        log.info("[MONGODB] Searching tarif for productType={}, age={}", productType, profil.age());

        return priceRepository.findByProductTypeAndAge(productType, profil.age())
                .map(doc -> new TarifQuoteView(doc.getTarif()))
                        .or(() -> {
                    log.warn("[MONGODB] No tarif found for productType={}, age={}", productType, profil.age());
                    return Optional.empty();
                });
    }
}
