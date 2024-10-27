package com.ecommerce.microservices.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Order", description = "Operations related to the order")
@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/test")
    @Operation(summary = "Test order ", description = "Endpoint for order testing")
    public String testEndpoint() {
        return "Testing";
    }
}
