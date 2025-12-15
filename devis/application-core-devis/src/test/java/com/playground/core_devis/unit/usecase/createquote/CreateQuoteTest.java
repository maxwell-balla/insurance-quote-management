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
        // Given
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var expectedSnapshot = new QuoteSnapshot(QUOTE_ID, CUSTOMER_ID, ProductType.AUTO, AGE, CAPITAL, DURATION, Status.CREATED, TARIF);

        // When
        createQuoteUseCase.execute(cmd);

        // Then
        assertThat(quoteRepo.allSnapshots()).containsExactly(expectedSnapshot);
    }

    @Test
    @DisplayName("should create quote with default tariff because Service is not available")
    void shouldCreateQuoteWithDefaultTariff() {
        // Given
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);
        var expectedSnapshot = new QuoteSnapshot(QUOTE_ID, CUSTOMER_ID, ProductType.AUTO, AGE, CAPITAL, DURATION, Status.CREATED, DEFAULT_TARIF);
        systemPricing.enableFallback();

        // When
        createQuoteUseCase.execute(cmd);

        // Then
        assertThat(quoteRepo.allSnapshots()).containsExactly(expectedSnapshot);
    }

    @Test
    @DisplayName("should throw error when customerId is null")
    void shouldThrowErrorWhenCustomerIdIsNull() {
        var cmd = new CreateQuoteRequest(null, ProductType.AUTO, new Profil(AGE), CAPITAL, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("should throw error when productType is null")
    void shouldThrowErrorWhenProductTypeIsNull() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, null, new Profil(AGE), CAPITAL, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("should throw error when capital is zero")
    void shouldThrowErrorWhenCapitalIsZero() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), 0, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw error when capital is negative")
    void shouldThrowErrorWhenCapitalIsNegative() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), -100, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw error when duration is zero")
    void shouldThrowErrorWhenDurationIsZero() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, 0);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw error when duration is negative")
    void shouldThrowErrorWhenDurationIsNegative() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(AGE), CAPITAL, -1);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw error when age is negative")
    void shouldThrowErrorWhenAgeIsNegative() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(-1), CAPITAL, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw error when age is over 120")
    void shouldThrowErrorWhenAgeIsOver120() {
        var cmd = new CreateQuoteRequest(CUSTOMER_ID, ProductType.AUTO, new Profil(121), CAPITAL, DURATION);

        assertThatThrownBy(() -> createQuoteUseCase.execute(cmd))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
