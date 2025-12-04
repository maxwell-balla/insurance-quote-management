package com.playground.adapter_devis.repository;

import com.playground.adapter_devis.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaQuoteRepository extends JpaRepository<QuoteEntity, UUID> {
}
