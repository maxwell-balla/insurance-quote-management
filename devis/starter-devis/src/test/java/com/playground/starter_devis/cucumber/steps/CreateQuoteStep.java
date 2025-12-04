package com.playground.starter_devis.cucumber.steps;

import com.playground.adapter_devis.model.*;
import com.playground.starter_devis.configuration.DataTableConfiguration.ExpectedField;
import com.playground.starter_devis.stub.PricingClientStub;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateQuoteStep {

    private CreateQuoteRequest request;
    private CreateQuoteResponse response;

    @LocalServerPort
    private int port;

    @Given("the pricing service is available")
    public void the_pricing_service_is_available() {
        PricingClientStub.stubOutPricingCall();
    }

    @Given("the pricing service is not available")
    public void the_pricing_service_is_not_available() {
        PricingClientStub.stubOutPricingFailure();
    }

    @Given("the agent enter the following information's:")
    public void the_agent_enter_the_following_information(List<CreateQuoteRequest> requests) {
        request = requests.getFirst();
    }


    @When("the agent makes a new quote")
    public void the_agent_makes_a_new_quote() {

        var client = RestTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        response = client.post().uri("/api/v1/quote")
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateQuoteResponse.class)
                .returnResult()
                .getResponseBody();
    }

    @Then("the agent receives the following quote details:")
    public void the_agent_receives_the_following_quote_details(List<ExpectedField> expectedFields) {
        for (ExpectedField ef : expectedFields) {
            switch (ef.field()) {
                case "quoteId" -> assertThat(response.getQuoteId()).isNotNull();
                case "customerId" -> assertThat(response.getCustomerId()).isEqualTo(UUID.fromString(ef.expectedValue()));
                case "productType" -> assertThat(response.getProductType()).isEqualTo(ProductType.valueOf(ef.expectedValue()));
                case "capital" -> assertThat(response.getCapital()).isEqualTo(Double.valueOf(ef.expectedValue()));
                case "duration" -> assertThat(response.getDuration()).isEqualTo(Integer.valueOf(ef.expectedValue()));
                case "status" -> assertThat(response.getStatus()).isEqualTo(Status.valueOf(ef.expectedValue()));
                case "tarif" -> assertThat(response.getTarif()).isEqualTo(new BigDecimal(ef.expectedValue()));
                default -> throw new IllegalArgumentException("Unknown field: " + ef.field());
            }
        }
    }
}
