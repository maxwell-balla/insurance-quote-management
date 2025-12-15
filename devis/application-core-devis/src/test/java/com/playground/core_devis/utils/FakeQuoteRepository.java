package com.playground.core_devis.utils;

import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.domain.model.QuoteSnapshot;
import com.playground.core_devis.port.spi.QuoteRepositoryPort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.playground.core_devis.utils.TestConstantDevis.QUOTE_ID;

public class FakeQuoteRepository implements QuoteRepositoryPort {

    private final List<QuoteSnapshot> savedSnapshots = new ArrayList<>();

    public Collection<QuoteSnapshot> allSnapshots() {
        return savedSnapshots;
    }

    @Override
    public QuoteSnapshot save(Quote quote) {
        var snapshot = quote.withId(QUOTE_ID).snapshot();
        savedSnapshots.add(snapshot);
        return snapshot;
    }
}
