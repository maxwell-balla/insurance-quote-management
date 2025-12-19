package com.playground.starter_pricing;

import com.playground.core_pricing.DomainPricingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.playground.adapter_pricing.repository")
@ComponentScan(
        basePackages = {
                "com.playground.starter_pricing",
                "com.playground.adapter_pricing",
                "com.playground.core_pricing"
        },
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainPricingService.class
        )
)
public class StarterPricingApp {
    public static void main( String[] args ) {
        SpringApplication.run(StarterPricingApp.class, args);
    }
}
