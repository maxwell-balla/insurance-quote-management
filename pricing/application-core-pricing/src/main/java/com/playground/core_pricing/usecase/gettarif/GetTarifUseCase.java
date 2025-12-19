package com.playground.core_pricing.usecase.gettarif;

import com.playground.core_pricing.DomainPricingService;
import com.playground.core_pricing.domain.model.Price;
import com.playground.core_pricing.domain.model.TarifNotFoundException;
import com.playground.core_pricing.domain.model.TarifQuoteView;
import com.playground.core_pricing.port.api.UseCase;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;

@DomainPricingService
public class GetTarifUseCase implements UseCase<GetTarifRequest, GetTarifResponse> {

    private final PricingRepositoryPort pricingRepo;

    public GetTarifUseCase(PricingRepositoryPort pricingRepo) {
        this.pricingRepo = pricingRepo;
    }

    @Override
    public GetTarifResponse execute(GetTarifRequest request) {
        var price = Price.of(request.productType(), request.profil());
        var tarifView = getTariff(price);
        return new GetTarifResponse(tarifView.tarif());
    }

    private TarifQuoteView getTariff(Price price) {
        return pricingRepo.getTarif(price.getProductType(), price.getProfil())
                .orElseThrow(() -> new TarifNotFoundException("Tariff Not found"));
    }
}
