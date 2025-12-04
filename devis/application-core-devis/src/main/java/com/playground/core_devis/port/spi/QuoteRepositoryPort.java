package com.playground.core_devis.port.spi;

import com.playground.core_devis.domain.model.Quote;

import java.util.UUID;

public interface QuoteRepositoryPort {
    UUID save(Quote Quote);
}
