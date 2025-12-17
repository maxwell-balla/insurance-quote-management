package com.playground.starter_devis.configuration;

import com.playground.adapter_devis.model.CreateQuoteRequest;
import com.playground.adapter_devis.model.ProductType;
import com.playground.adapter_devis.model.Profil;
import io.cucumber.java.DataTableType;

import java.util.Map;
import java.util.UUID;

public class DataTableConfiguration {

    public record ExpectedField(String field, String expectedValue) {}

    @DataTableType
    public CreateQuoteRequest quoteRequestTransformer(Map<String, String> row) {
        return new CreateQuoteRequest(
                UUID.fromString(row.get("customerId")),
                ProductType.valueOf(row.get("productType")),
                new Profil(Integer.parseInt(row.get("age"))),
                Double.parseDouble(row.get("capital")),
                Integer.parseInt(row.get("duration"))
        );
    }

    @DataTableType
    public ExpectedField expectedFieldTransformer(Map<String, String> row) {
        return new ExpectedField(row.get("field"), row.get("expected value"));
    }
}