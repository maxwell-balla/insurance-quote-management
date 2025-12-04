package com.playground.starter_devis.stub;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.playground.starter_devis.configuration.WireMockServerConfiguration;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;

public class PricingClientStub {

    public static void stubOutPricingCall() {
        WireMockServerConfiguration.getWireMockServer().stubFor(
                WireMock.get(urlPathEqualTo("/api/v1/pricing"))
                        .withQueryParam("productType", equalTo("AUTO"))
                        .withQueryParam("age", equalTo("30"))
                        .withHeader("Authorization", matching("Bearer .*"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("""
                                {
                                  "tarif": 300.0
                                }
                                """))
        );
    }

    public static void stubOutPricingFailure() {
        WireMockServerConfiguration.getWireMockServer().stubFor(
                WireMock.get(urlPathEqualTo("/api/v1/pricing"))
                        .withQueryParam("productType", equalTo("AUTO"))
                        .withQueryParam("age", equalTo("30"))
                        .withHeader("Authorization", matching("Bearer .*"))
                        .willReturn(aResponse()
                                .withStatus(503)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("""
                                {
                                  "tarif": 500.0
                                }
                                """))
        );
    }
}