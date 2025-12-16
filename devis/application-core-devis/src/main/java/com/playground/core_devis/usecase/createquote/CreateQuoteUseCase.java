package com.playground.core_devis.usecase.createquote;

import com.playground.core_devis.DomainDevisService;
import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.port.spi.UnitOfWorkPort;
import com.playground.core_devis.port.api.UseCase;
import com.playground.core_devis.port.spi.PricingPort;
import com.playground.core_devis.port.spi.QuoteRepositoryPort;

import java.math.BigDecimal;

@DomainDevisService
public class CreateQuoteUseCase implements UseCase<CreateQuoteRequest, CreateQuoteResponse> {

    private final PricingPort pricingPort;
    private final QuoteRepositoryPort quoteRepoPort;
    private final UnitOfWorkPort unitOfWork;

    public CreateQuoteUseCase(PricingPort pricingPort, QuoteRepositoryPort quoteRepoPort, UnitOfWorkPort unitOfWork) {
        this.pricingPort = pricingPort;
        this.quoteRepoPort = quoteRepoPort;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public CreateQuoteResponse execute(CreateQuoteRequest request) {
        return unitOfWork.execute(() -> {
            BigDecimal tarif = retrieveTariffication(request);
            var quote = Quote.create(
                    request.customerId(),
                    request.productType(),
                    request.profil().age(),
                    request.capital(),
                    request.duration(),
                    tarif
            );
            var savedSnapshot = quoteRepoPort.save(quote);
            return CreateQuoteResponse.fromSnapshot(savedSnapshot);
        });
    }

    private BigDecimal retrieveTariffication(CreateQuoteRequest request) {
        var pricing = pricingPort.getTarif(request.productType(), request.profil());
        return pricing.tarif();
    }
}
