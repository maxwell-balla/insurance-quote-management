package com.playground.starter_devis;

import com.playground.starter_devis.configuration.TestContainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestBootstrapApplication {
    static void main(String[] args) {
        SpringApplication
                .from(StarterDevisApp::main)
                .with(TestContainersConfiguration.class)
                .run(args);
    }
}
