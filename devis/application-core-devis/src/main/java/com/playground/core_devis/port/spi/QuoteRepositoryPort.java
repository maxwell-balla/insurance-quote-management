package com.playground.core_devis.port.spi;

import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.domain.model.QuoteSnapshot;

public interface QuoteRepositoryPort {
    QuoteSnapshot save(Quote quote);
}
