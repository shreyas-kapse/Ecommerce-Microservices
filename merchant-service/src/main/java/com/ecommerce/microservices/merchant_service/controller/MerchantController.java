package com.ecommerce.microservices.merchant_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @GetMapping("/test")
    public String testEndpoint(){
        return "Testing";
    }
}
