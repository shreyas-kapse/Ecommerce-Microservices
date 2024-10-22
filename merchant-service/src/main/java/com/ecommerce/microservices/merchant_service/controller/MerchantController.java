package com.ecommerce.microservices.merchant_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
@Tag(name = "Merchant", description = "Operations related to merchant ")
public class MerchantController {

    @GetMapping("/test")
    @Operation(summary = "Test merchant ", description = "Endpoint for merchant testing")
    public String testEndpoint(){
        return "Testing";
    }
}
