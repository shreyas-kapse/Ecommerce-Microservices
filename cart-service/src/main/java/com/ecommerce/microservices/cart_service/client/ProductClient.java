package com.ecommerce.microservices.cart_service.client;

import com.ecommerce.microservices.cart_service.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {

    @GetExchange("/product/{id}")
    public ProductDTO getProductsById(
            @Parameter(description = "Product Id") @PathVariable("id") String id
    );
}
