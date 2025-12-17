package com.playground.inproc;

import com.playground.core_devis.domain.model.Pricing;
import com.playground.core_devis.domain.model.ProductType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(accept = "application/json")
public interface PricingHttpClient {

    @GetExchange("/api/v1/pricing")
    Pricing getTarif(
            @RequestParam("productType") ProductType productType,
            @RequestParam("age") int age,
            @RequestHeader("Authorization") String auth
    );
}
