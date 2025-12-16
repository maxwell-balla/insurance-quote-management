package com.playground.core_pricing.usecase.getTarif;

import com.playground.core_pricing.domain.model.TarifNotFoundException;
import com.playground.core_pricing.usecase.gettarif.GetTarifRequest;
import com.playground.core_pricing.usecase.gettarif.GetTarifResponse;
import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profil;
import com.playground.core_pricing.usecase.gettarif.GetTarifUseCase;
import com.playground.core_pricing.utils.FakePricingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.playground.core_pricing.utils.TestConstantPricing.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetTarifTest {

    private GetTarifUseCase getTarifUseCase;
    private FakePricingRepository pricingRepo;

    @BeforeEach
    void setUp() {
        pricingRepo = new FakePricingRepository();
        getTarifUseCase = new GetTarifUseCase(pricingRepo);
    }

    @Test
    @DisplayName("should return tarif for valid product type and profil")
    void shouldGetTarif() {
        var request = givenValidRequest();
        pricingRepo.feedPrice();

        var response = whenGettingTarif(request);

        thenTarifIsReturned(response);
    }

    @Test
    @DisplayName("should return tarif for minimum valid age (18)")
    void shouldGetTarifForMinimumAge() {
        var request = givenRequestWithAge(18);
        givenPriceExistsWithAge(18);

        var response = whenGettingTarif(request);

        thenTarifIsReturned(response);
    }

    @Test
    @DisplayName("should return tarif for maximum valid age (90)")
    void shouldGetTarifForMaximumAge() {
        var request = givenRequestWithAge(90);
        givenPriceExistsWithAge(90);

        var response = whenGettingTarif(request);

        thenTarifIsReturned(response);
    }

    @Test
    @DisplayName("should throw TarifNotFoundException when price not in repository")
    void shouldThrowErrorWhenPriceNotFound() {
        var request = givenValidRequest();

        whenAndThenTarifNotFound(request);
    }

    @Test
    @DisplayName("should throw TarifNotFoundException when profil is null")
    void shouldThrowErrorWhenProfileIsNull() {
        var request = new GetTarifRequest(ProductType.AUTO, null);

        whenAndThenTarifNotFound(request);
    }

    @Test
    @DisplayName("should throw error when product type is null")
    void shouldThrowErrorWhenProductTypeIsNull() {
        var request = new GetTarifRequest(null, new Profil(AGE));

        whenAndThenTarifIsNotReturnedWithIllegal(request);
    }

    @Test
    @DisplayName("should throw error when age is under 18")
    void shouldThrowErrorWhenAgeIsUnder18() {
        whenAndThenProfileIsNotCreatedWithIllegal(17);
    }

    @Test
    @DisplayName("should throw error when age is over 90")
    void shouldThrowErrorWhenAgeIsOver90() {
        whenAndThenProfileIsNotCreatedWithIllegal(91);
    }

    private GetTarifRequest givenValidRequest() {
        return new GetTarifRequest(ProductType.AUTO, new Profil(AGE));
    }

    private GetTarifRequest givenRequestWithAge(int age) {
        return new GetTarifRequest(ProductType.AUTO, new Profil(age));
    }

    private void givenPriceExistsWithAge(int age) {
        pricingRepo.feedPriceWithAge(age);
    }

    private GetTarifResponse whenGettingTarif(GetTarifRequest request) {
        return getTarifUseCase.execute(request);
    }

    private void thenTarifIsReturned(GetTarifResponse response) {
        assertThat(response.tarif()).isEqualTo(TARIF);
    }

    private void whenAndThenTarifNotFound(GetTarifRequest request) {
        assertThatThrownBy(() -> getTarifUseCase.execute(request))
                .isInstanceOf(TarifNotFoundException.class);
    }

    private void whenAndThenTarifIsNotReturnedWithIllegal(GetTarifRequest request) {
        assertThatThrownBy(() -> getTarifUseCase.execute(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void whenAndThenProfileIsNotCreatedWithIllegal(int age) {
        assertThatThrownBy(() -> new Profil(age))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
