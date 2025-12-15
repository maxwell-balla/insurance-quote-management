package com.playground.core_devis.usecase.createquote;

import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.domain.model.QuoteSnapshot;
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
    public static CreateQuoteResponse fromSnapshot(QuoteSnapshot snapshot) {
        return new CreateQuoteResponse(
                snapshot.id(),
                snapshot.customerId(),
                snapshot.productType(),
                new Profil(snapshot.age()),
                snapshot.capital(),
                snapshot.duration(),
                snapshot.status(),
                snapshot.tarif()
        );
    }
}
