package com.playground.core_pricing.usecase.gettarif;

import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profile;

public record GetTarifRequest(ProductType productType, Profile profile) {
}
