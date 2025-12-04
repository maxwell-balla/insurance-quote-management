package com.playground.core_devis.usecase.createquote;

import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;

import java.util.UUID;

public record CreateQuoteRequest(
        UUID customerId,
        ProductType productType,
        Profil profil,
        double capital,
        int duration
) {
}
