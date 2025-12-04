package com.playground.adapter_devis.repository;

import com.playground.adapter_devis.mapper.QuoteJpaMapper;
import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.port.spi.QuoteRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QuoteRepositoryPostgresAdapter implements QuoteRepositoryPort {

    private final JpaQuoteRepository jpaQuoteRepository;

    public QuoteRepositoryPostgresAdapter(JpaQuoteRepository jpaQuoteRepository) {
        this.jpaQuoteRepository = jpaQuoteRepository;
    }

    @Override
    public UUID save(Quote Quote) {
        var savedEntity = jpaQuoteRepository.save(QuoteJpaMapper.INSTANCE.mapToEntity(Quote));
        return savedEntity.getId();
    }
}
