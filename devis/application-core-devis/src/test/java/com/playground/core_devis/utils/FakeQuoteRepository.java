package com.playground.core_devis.utils;

import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.port.spi.QuoteRepositoryPort;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.playground.core_devis.utils.TestConstantDevis.QUOTE_ID;

public class FakeQuoteRepository implements QuoteRepositoryPort {

    private final Map<UUID, Quote> quoteByIds = new HashMap<>();

    public Collection<Quote> all() {
        return quoteByIds.values();
    }

    @Override
    public UUID save(Quote Quote) {
        Quote.setId(QUOTE_ID);
        quoteByIds.put(QUOTE_ID, Quote);
        return Quote.getId();
    }
}
