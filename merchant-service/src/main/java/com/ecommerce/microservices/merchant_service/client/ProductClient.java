package com.ecommerce.microservices.merchant_service.client;

import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/product/test")
    public String productTest();

    @PostExchange("/product/add-product")
    public DefaultResponse addProduct(@RequestBody ProductDTO productDTO);

    @GetExchange("/product/all/{merchantId}")
    public DefaultResponse getProductsOfMerchantByMerchantId(
            @Parameter(description = "Merchant Id") @PathVariable("merchantId") String merchantId,
            @RequestParam int page,
            @RequestParam int size
    );
}
