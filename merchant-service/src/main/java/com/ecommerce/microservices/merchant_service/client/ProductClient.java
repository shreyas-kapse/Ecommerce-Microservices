package com.ecommerce.microservices.merchant_service.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/product/test")
    public String productTest();
}
