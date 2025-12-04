package com.playground.core_devis.usecase.createquote;

import com.playground.core_devis.utils.FakeQuoteRepository;
import com.playground.core_devis.utils.FakeSystemPricing;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.domain.model.Quote;
import com.playground.core_devis.domain.model.Status;
import com.playground.core_devis.utils.FakeUnitOfWork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.playground.core_devis.utils.TestConstantDevis.*;
import static org.assertj.core.api.Assertions.assertThat;

class CreateQuoteTest {

    private CreateQuoteUseCase createQuoteUseCase;
    private FakeQuoteRepository quoteRepo;
    private FakeSystemPricing systemPricing;

    @BeforeEach
    void setUp() {
        quoteRepo = new FakeQuoteRepository();
        systemPricing = new FakeSystemPricing();
        FakeUnitOfWork unitOfWork = new FakeUnitOfWork();
        createQuoteUseCase = new CreateQuoteUseCase(systemPricing, quoteRepo, unitOfWork);
    }

    @Test
    @DisplayName("create quote with tariff of Pricing Service")
    void shouldCreateQuote() {
        // Given
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var quote = new Quote(QUOTE_ID, CAPITAL, DURATION, Status.CREATED, CUSTOMER_ID, ProductType.AUTO, AGE, TARIF);

        // When
        createQuoteUseCase.execute(cmd);

        // Then
        assertThat(quoteRepo.all()).containsExactly(quote);
    }

    @Test
    @DisplayName("create quote with default tariff because Service is not available")
    void shouldCreateQuoteWithDefaultTariff() {
        // Given
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var quote = new Quote(QUOTE_ID, CAPITAL, DURATION, Status.CREATED, CUSTOMER_ID, ProductType.AUTO, AGE, DEFAULT_TARIF);
        systemPricing.enableFallback();

        // When
        createQuoteUseCase.execute(cmd);

        // Then
        assertThat(quoteRepo.all()).containsExactly(quote);
    }
}
