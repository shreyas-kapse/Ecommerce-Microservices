package com.ecommerce.microservices.cart_service.controller;

import com.ecommerce.microservices.cart_service.dto.CartDTOResponse;
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
    public ResponseEntity<DefaultResponse> addProductToCart(
            @RequestParam(name = "Product Id", defaultValue = "73ebbbd7-8729-4f30-9a0f-53c1731b79ed") String productId,
            @RequestParam(name = "User Id", defaultValue = "987e1234-e89b-12d3-a456-426614174321") String userId,
            @RequestParam int quantity
    ) {
        String token;
        DefaultResponse response;

        log.info("Processing add product request for product id {}", productId);

        response = iCartService.addProductToCart(productId, userId, quantity);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get cart items", description = "Get all cart items")
    public ResponseEntity<CartDTOResponse> getCart(
            @RequestParam(name = "User Id", defaultValue = "987e1234-e89b-12d3-a456-426614174321") String userId
    ) {
        log.info("Processing get cart items request ");

        CartDTOResponse cartDTOResponse = iCartService.getCart(userId);

        return !cartDTOResponse.getResponse().isSuccess() ? ResponseEntity.status(cartDTOResponse.getResponse().getHttpStatus().get()).body(cartDTOResponse) : ResponseEntity.ok(cartDTOResponse);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Delete product from cart", description = "Delete product from cart by product id")
    public ResponseEntity<DefaultResponse> removeProductFromCart(
            @RequestParam(name = "Product Id", defaultValue = "73ebbbd7-8729-4f30-9a0f-53c1731b79ed") String productId,
            @RequestParam(name = "User Id", defaultValue = "987e1234-e89b-12d3-a456-426614174321") String userId
    ) {
        log.info("Processing remove product request for product id {}", productId);

        DefaultResponse response = iCartService.removeProductFromCart(productId, userId);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear cart", description = "Remove all products from cart")
    public ResponseEntity<DefaultResponse> clearCart(
            @RequestParam(name = "User Id", defaultValue = "987e1234-e89b-12d3-a456-426614174321") String userId
    ) {
        log.info("Processing clear cart request ");

        DefaultResponse response = iCartService.clearCart(userId);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);

    }
}
