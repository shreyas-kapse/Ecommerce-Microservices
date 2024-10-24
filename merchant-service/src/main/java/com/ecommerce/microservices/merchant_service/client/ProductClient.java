package com.ecommerce.microservices.merchant_service.client;

import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/product/test")
    public String productTest();

    @PostExchange("/product/add-product")
    public DefaultResponse addProduct(@RequestBody ProductDTO productDTO);
}
