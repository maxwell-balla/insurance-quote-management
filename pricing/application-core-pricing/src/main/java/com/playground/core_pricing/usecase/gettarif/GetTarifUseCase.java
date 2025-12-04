package com.playground.core_pricing.usecase.gettarif;

import com.playground.core_pricing.DomainPricingService;
import com.playground.core_pricing.domain.model.Price;
import com.playground.core_pricing.domain.model.TarifNotFoundException;
import com.playground.core_pricing.port.api.UseCase;
import com.playground.core_pricing.port.spi.PricingRepositoryPort;

import java.math.BigDecimal;

@DomainPricingService
public class GetTarifUseCase implements UseCase<GetTarifRequest, GetTarifResponse> {

    private final PricingRepositoryPort pricingRepo;

    public GetTarifUseCase(PricingRepositoryPort pricingRepo) {
        this.pricingRepo = pricingRepo;
    }

    @Override
    public GetTarifResponse execute(GetTarifRequest request) {
        var price = Price.of(request.productType(), request.profile());
        var tariff = getTariff(price);
        return new GetTarifResponse(tariff);
    }

    private BigDecimal getTariff(Price price) {
        return pricingRepo.getTarif(price.getProductType(), price.getProfile())
                .orElseThrow(() -> new TarifNotFoundException("Tariff Not found"));
    }
}
