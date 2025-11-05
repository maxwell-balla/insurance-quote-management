package com.playground.devis;

import org.springframework.boot.SpringApplication;

public class TestDevisApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(DevisApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }

}
