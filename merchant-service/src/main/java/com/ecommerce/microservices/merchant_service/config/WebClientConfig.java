package com.ecommerce.microservices.merchant_service.config;

import com.ecommerce.microservices.merchant_service.client.ProductClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient productClientConfig() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        return webClient;
    }

    @Bean
    public ProductClient productClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(productClientConfig()))
                .build();

        return httpServiceProxyFactory.createClient(ProductClient.class);
    }
}