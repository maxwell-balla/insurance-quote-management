package com.playground.core_devis.unit.usecase.createquote;

import com.playground.core_devis.unit.usecase.FakeQuoteRepository;
import com.playground.core_devis.unit.usecase.FakeSystemPricing;
import com.playground.core_devis.unit.usecase.FakeUnitOfWork;
import com.playground.core_devis.usecase.createquote.CreateQuoteRequest;
import com.playground.core_devis.usecase.createquote.CreateQuoteUseCase;
import com.playground.core_devis.domain.model.ProductType;
import com.playground.core_devis.domain.model.Profil;
import com.playground.core_devis.domain.model.QuoteSnapshot;
import com.playground.core_devis.domain.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.playground.core_devis.unit.usecase.TestConstantDevis.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("should create quote with tariff of Pricing Service")
    void shouldCreateQuote() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var expectedSnapshot = new QuoteSnapshot(QUOTE_ID, CUSTOMER_ID, ProductType.AUTO, AGE, CAPITAL, DURATION, Status.CREATED, TARIF);
        
        whenCreatingQuote(cmd);
        
        thenQuoteWasCreated(expectedSnapshot);
    }

    @Test
    @DisplayName("should create quote with default tariff because Service is not available")
    void shouldCreateQuoteWithDefaultTariff() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var expectedSnapshot = new QuoteSnapshot(QUOTE_ID, CUSTOMER_ID, ProductType.AUTO, AGE, CAPITAL, DURATION, Status.CREATED, DEFAULT_TARIF);
        systemPricing.enableFallback();
        
        whenCreatingQuote(cmd);
        
        thenQuoteWasCreated(expectedSnapshot);
    }

    @Test
    @DisplayName("should throw error when customerId is null")
    void shouldThrowErrorWhenCustomerIdIsNull() {
        var cmd = new CreateQuoteRequest(null, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);

        thenQuoteIsNotCreatedWithNullElement(cmd);
    }

    @Test
    @DisplayName("should throw error when productType is null")
    void shouldThrowErrorWhenProductTypeIsNull() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, null, new Profil(AGE), CAPITAL, DURATION);

        thenQuoteIsNotCreatedWithNullElement(cmd);
    }

    @Test
    @DisplayName("should throw error when capital is zero")
    void shouldThrowErrorWhenCapitalIsZero() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), 0, DURATION);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    @Test
    @DisplayName("should throw error when capital is negative")
    void shouldThrowErrorWhenCapitalIsNegative() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), -100, DURATION);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    @Test
    @DisplayName("should throw error when duration is zero")
    void shouldThrowErrorWhenDurationIsZero() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, 0);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    @Test
    @DisplayName("should throw error when duration is negative")
    void shouldThrowErrorWhenDurationIsNegative() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, -1);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    @Test
    @DisplayName("should throw error when age is under 18")
    void shouldThrowErrorWhenAgeIsUnder18() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(17), CAPITAL, DURATION);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    @Test
    @DisplayName("should throw error when age is over 90")
    void shouldThrowErrorWhenAgeIsOver90() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(91), CAPITAL, DURATION);

        whenAndThenQuoteIsNotCreatedWithIllegal(cmd);
    }

    private void thenQuoteIsNotCreatedWithNullElement(CreateQuoteRequest cmd) {
        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(NullPointerException.class);
    }

    private void whenCreatingQuote(CreateQuoteRequest cmd) {
        createQuoteUseCase.execute(cmd);
    }

    private void thenQuoteWasCreated(QuoteSnapshot expectedSnapshot) {
        assertThat(quoteRepo.allSnapshots()).containsExactly(expectedSnapshot);
    }

    private void whenAndThenQuoteIsNotCreatedWithIllegal(CreateQuoteRequest cmd) {
        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
