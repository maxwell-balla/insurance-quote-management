package com.playground.starter_devis.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@TestConfiguration(proxyBeanMethods = false)
public class WireMockServerConfiguration {

    private static final WireMockServer wireMockServer;

    static {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        System.out.println("=== WireMock started on port: " + wireMockServer.port());
        System.out.println("=== pricing.url défini comme propriété système: " + wireMockServer.baseUrl());
    }

    @Bean
    public static WireMockServer getWireMockServer() {
        return wireMockServer;
    }
}