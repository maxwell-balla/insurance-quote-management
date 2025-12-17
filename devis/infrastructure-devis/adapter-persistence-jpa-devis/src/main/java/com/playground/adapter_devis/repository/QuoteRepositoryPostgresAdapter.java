package com.playground.adapter_devis.repository;

import com.playground.adapter_devis.entity.QuoteEntity;
import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.domain.model.QuoteSnapshot;
import com.playground.core_devis.port.spi.QuoteRepositoryPort;

import org.springframework.stereotype.Component;

@Component
public class QuoteRepositoryPostgresAdapter implements QuoteRepositoryPort {

    private final SpringDataQuoteRepository springDataQuoteRepository;

    public QuoteRepositoryPostgresAdapter(SpringDataQuoteRepository springDataQuoteRepository) {
        this.springDataQuoteRepository = springDataQuoteRepository;
    }

    @Override
    public QuoteSnapshot save(Quote quote) {
        var entity = QuoteEntity.fromSnapshot(quote.snapshot());
        var savedEntity = springDataQuoteRepository.save(entity);
        return savedEntity.toSnapshot();
    }
}
