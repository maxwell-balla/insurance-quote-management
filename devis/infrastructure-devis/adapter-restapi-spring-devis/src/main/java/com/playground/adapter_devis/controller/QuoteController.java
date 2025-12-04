package com.playground.adapter_devis.controller;

import com.playground.adapter_devis.api.QuotesApi;
import com.playground.adapter_devis.mapper.QuoteDtoMapper;
import com.playground.core_devis.port.api.UseCase;
import com.playground.core_devis.usecase.createquote.CreateQuoteRequest;
import com.playground.core_devis.usecase.createquote.CreateQuoteResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class QuoteController implements QuotesApi {

    private final UseCase<CreateQuoteRequest, CreateQuoteResponse> createQuoteUseCase;

    public QuoteController(UseCase<CreateQuoteRequest, CreateQuoteResponse> createQuoteUseCase) {
        this.createQuoteUseCase = createQuoteUseCase;
    }

    @Override
    @PostMapping("/v1/quote")
    public ResponseEntity<com.playground.adapter_devis.model.CreateQuoteResponse> createQuote(
            @RequestBody @Valid com.playground.adapter_devis.model.CreateQuoteRequest createQuoteRequest) {
        log.debug("REST request to save Quote : {}", createQuoteRequest);
        var result = createQuoteUseCase
                .execute(QuoteDtoMapper.INSTANCE.mapToCreateQuoteCmd(createQuoteRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(QuoteDtoMapper.INSTANCE.mapToCreateQuoteResponse(result));

    }
}
