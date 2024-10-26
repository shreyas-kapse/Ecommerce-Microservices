package com.ecommerce.microservices.cart_service.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Operations related to cart")
public class CartController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "Testing";
    }
}
