package com.playground.inproc.configuration;

import com.playground.inproc.PricingHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class HttpClientConfig {

    @Bean
    public PricingHttpClient pricingHttpClient(
            @Value("${pricing.url}") String pricingUrl,
            RestClient.Builder restClientBuilder) {

        RestClient restClient = restClientBuilder
                .baseUrl(pricingUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(PricingHttpClient.class);
    }
}
