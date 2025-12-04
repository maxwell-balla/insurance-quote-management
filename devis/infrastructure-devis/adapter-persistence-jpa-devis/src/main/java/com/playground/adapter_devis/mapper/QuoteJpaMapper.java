package com.playground.adapter_devis.mapper;

import com.playground.adapter_devis.entity.QuoteEntity;
import com.playground.core_devis.domain.model.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuoteJpaMapper {

    QuoteJpaMapper INSTANCE = Mappers.getMapper(QuoteJpaMapper.class);

    QuoteEntity mapToEntity(Quote Quote);
}
