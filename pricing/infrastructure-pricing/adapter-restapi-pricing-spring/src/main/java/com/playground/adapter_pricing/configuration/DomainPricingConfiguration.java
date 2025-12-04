package com.playground.adapter_pricing.configuration;

import com.playground.core_pricing.DomainPricingService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = {DomainPricingService.class}
                )
        })
public class DomainPricingConfiguration {
}
