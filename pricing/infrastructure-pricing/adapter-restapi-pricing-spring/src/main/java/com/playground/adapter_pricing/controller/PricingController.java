package com.playground.adapter_pricing.controller;

import com.playground.adapter_pricing.mapper.PricingDtoMapper;
import com.playground.adapter_pricing.model.GetPricingResponse;
import com.playground.adapter_pricing.model.ProductType;
import com.playground.adapter_pricing.api.PricingApi;
import com.playground.core_pricing.port.api.UseCase;
import com.playground.core_pricing.usecase.gettarif.GetTarifRequest;
import com.playground.core_pricing.usecase.gettarif.GetTarifResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PricingController implements PricingApi {

    private final UseCase<GetTarifRequest, GetTarifResponse> getTarifUseCase;

    public PricingController(UseCase<GetTarifRequest, GetTarifResponse> getTarifUseCase) {
        this.getTarifUseCase = getTarifUseCase;
    }

    @Override
    @GetMapping("/v1/pricing")
    public ResponseEntity<GetPricingResponse> getTarif(ProductType productType, Integer age) {
        var result = getTarifUseCase
                .execute(PricingDtoMapper.INSTANCE.mapToGetTarifQuery(productType, age));
        return ResponseEntity.ok()
                .body(PricingDtoMapper.INSTANCE.mapToGetPricingResponse(result));
    }
}
