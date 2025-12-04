package com.playground.starter_pricing.testcontainer;

import com.playground.starter_pricing.StarterPricingApp;
import org.springframework.boot.SpringApplication;

public class TestAppApplication {
    public static void main(String[] args) {
        SpringApplication
                .from(StarterPricingApp::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
