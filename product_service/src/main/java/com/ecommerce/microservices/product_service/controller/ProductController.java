package com.ecommerce.microservices.product_service.controller;

import com.ecommerce.microservices.product_service.entity.ProductEntity;
import com.ecommerce.microservices.product_service.service.IProductService;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product")
@Tag(name = "Product ", description = "Operations related to the product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @GetMapping("/test")
    public String testEndpoint() {
        return "Testing";
    }

    @PostMapping("/add-product")
    @Operation(summary = "Add product", description = "Add product on the platform / List product on the platform")
    public ResponseEntity<DefaultResponse> addProduct(
            @Valid @RequestBody ProductEntity productEntity, BindingResult result
    ) {
        DefaultResponse response;
        if (result.hasErrors()) {
            Map<String, String> error = new HashMap<>();
            result.getFieldErrors().forEach(err ->
                    error.put(err.getField(), err.getDefaultMessage())
            );
            response = DefaultResponse.builder()
                    .success(false)
                    .message("Validation failed")
                    .errors(Optional.of(error))
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        log.info("Processing add product request for product with name {} and brand name {}", productEntity.getProductName(), productEntity.getBrand());
        response = iProductService.addProduct(productEntity);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);
    }
}
