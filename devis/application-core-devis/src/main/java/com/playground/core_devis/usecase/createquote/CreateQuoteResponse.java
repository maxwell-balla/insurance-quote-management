package com.playground.core_devis.usecase.createquote;

import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.domain.model.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateQuoteResponse(
        UUID quoteId,
        UUID customerId,
        ProductType productType,
        Profil profil,
        double capital,
        int duration,
        Status status,
        BigDecimal tarif
) {
    public static CreateQuoteResponse of(UUID quoteId, Quote quote) {
        return new CreateQuoteResponse(
                quoteId,
                quote.getCustomerId(),
                quote.getProductType(),
                new Profil(quote.getAge()),
                quote.getCapital(),
                quote.getDuration(),
                quote.getStatus(),
                quote.getTarif()
        );
    }
}
