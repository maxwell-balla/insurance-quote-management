package com.playground.adapter_pricing.mapper;

import com.playground.core_pricing.usecase.gettarif.GetTarifRequest;
import com.playground.adapter_pricing.model.ProductType;
import com.playground.adapter_pricing.model.GetPricingResponse;
import com.playground.core_pricing.usecase.gettarif.GetTarifResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PricingDtoMapper {

    PricingDtoMapper INSTANCE = Mappers.getMapper(PricingDtoMapper.class);

    @Mapping(target = "profile", expression = "java(new Profile(age))")
    GetTarifRequest mapToGetTarifQuery(ProductType productType, Integer age);

    @Mapping(target = "tarif", source = "tarif")
    GetPricingResponse mapToGetPricingResponse(GetTarifResponse getTarifResponse);

    // Conversion BigDecimal -> Optional<BigDecimal>
    default Optional<BigDecimal> map(BigDecimal value) {
        return Optional.ofNullable(value);
    }
}
