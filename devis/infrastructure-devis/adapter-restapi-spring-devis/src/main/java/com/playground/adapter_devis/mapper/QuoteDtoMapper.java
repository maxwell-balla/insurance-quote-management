package com.playground.adapter_devis.mapper;

import com.playground.core_devis.usecase.createquote.CreateQuoteRequest;
import com.playground.core_devis.usecase.createquote.CreateQuoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuoteDtoMapper {

    QuoteDtoMapper INSTANCE = Mappers.getMapper(QuoteDtoMapper.class);

    @Mapping(target = "capital", source = "capital")
    @Mapping(target = "duration", source = "duration")
    CreateQuoteRequest mapToCreateQuoteCmd(com.playground.adapter_devis.model.CreateQuoteRequest request);

    com.playground.adapter_devis.model.CreateQuoteResponse mapToCreateQuoteResponse(CreateQuoteResponse result);
}
