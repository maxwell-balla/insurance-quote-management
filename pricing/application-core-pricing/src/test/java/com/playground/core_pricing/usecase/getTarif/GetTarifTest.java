package com.playground.core_pricing.usecase.getTarif;

import com.playground.core_pricing.domain.model.TarifNotFoundException;
import com.playground.core_pricing.usecase.gettarif.GetTarifRequest;
import com.playground.core_pricing.domain.model.ProductType;
import com.playground.core_pricing.domain.model.Profile;
import com.playground.core_pricing.usecase.gettarif.GetTarifUseCase;
import com.playground.core_pricing.utils.FakePricingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.playground.core_pricing.utils.TestConstantPricing.AGE;
import static com.playground.core_pricing.utils.TestConstantPricing.TARIF;
import static org.assertj.core.api.Assertions.*;

class GetTarifTest {

    private GetTarifUseCase getTarifUseCase;
    private FakePricingRepository pricingRepo;

    @BeforeEach
    void setUp() {
        pricingRepo = new FakePricingRepository();
        getTarifUseCase = new GetTarifUseCase(pricingRepo);
    }

    @Test
    void shouldGetTarif() {
        // Given
        var query = new GetTarifRequest(ProductType.AUTO, new Profile(AGE));
        pricingRepo.feedPrice();

        // When
        getTarifUseCase.execute(query);

        // Then
        assertThat(pricingRepo.getTarif(query.productType(), query.profile()))
                .isPresent()
                .hasValue(TARIF);
    }

    @Test
    void shouldNotGetTarifWhenPriceNotFound() {
        // Given
        var query = new GetTarifRequest(ProductType.AUTO, new Profile(AGE));

        // When & Then
        assertThatThrownBy(() -> getTarifUseCase.execute(query))
                .isInstanceOf(TarifNotFoundException.class);
    }

    @Test
    void shouldNotGetTarifWhenProductTypeIsNull() {
        // Given
        var query = new GetTarifRequest(null, new Profile(AGE));

        // When & Then
        assertThatThrownBy(() -> getTarifUseCase.execute(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotGetTarifWhenProfileIsNull() {
        // Given
        var query = new GetTarifRequest(ProductType.AUTO, null);

        // When & Then
        assertThatThrownBy(() -> getTarifUseCase.execute(query))
                .isInstanceOf(TarifNotFoundException.class);
    }
}
