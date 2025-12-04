package com.playground.adapter_devis.configuration;

import com.playground.core_devis.DomainDevisService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = {DomainDevisService.class}
                )
        })
public class DomainDevisConfiguration {
}
