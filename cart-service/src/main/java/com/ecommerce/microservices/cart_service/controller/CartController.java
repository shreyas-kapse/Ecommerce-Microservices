package com.ecommerce.microservices.cart_service.controller;

import com.ecommerce.microservices.cart_service.service.ICartService;
import com.ecommerce.microservices.cart_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Operations related to cart")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Testing";
    }

    @PostMapping("/add-product")
    @Operation(summary = "Add product to the cart")
    public ResponseEntity<DefaultResponse> addProductToCart(@RequestParam String productId, @RequestParam String userId, @RequestParam int quantity) {
        String token;
        DefaultResponse response;

        log.info("Processing add product request for product id {}", productId);

        response = iCartService.addProductToCart(productId, userId, quantity);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);
    }
}
