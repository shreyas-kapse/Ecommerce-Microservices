package com.ecommerce.microservices.product_service.controller;

import com.ecommerce.microservices.product_service.dto.ProductDTO;
import com.ecommerce.microservices.product_service.service.IProductService;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            @Valid @RequestBody ProductDTO productEntity, BindingResult result
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

    @GetMapping("/all/{merchantId}")
    @Operation(summary = "Get all products of merchant by merchant id")
    public ResponseEntity<DefaultResponse> getProductsOfMerchantByMerchantId(
            @Parameter(description = "Merchant Id", example = "987e1234-e89b-12d3-a456-426614174321") @PathVariable("merchantId") String merchantId,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        log.info("Processing get merchant by merchant name request for the merchant with id {}", merchantId);
        DefaultResponse response = iProductService.getProductsOfMerchantByMerchantId(merchantId, offset, limit);

        return ResponseEntity.status(response.getHttpStatus().get()).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ResponseEntity<ProductDTO> getProductsById(
            @Parameter(description = "Product Id") @PathVariable("id") String id
    ) {
        log.info("Processing get product by product id {}", id);
        ProductDTO productDTO = iProductService.getProductById(id);
        return !productDTO.getSuccess().get() ? ResponseEntity.status(productDTO.getHttpStatus().get()).body(productDTO) : ResponseEntity.ok(productDTO);
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get products by brand name", description = "List all products of given brand")
    public ResponseEntity<DefaultResponse> getProductsByBrandName(
            @Parameter(description = "Brand name", example = "SampleBrand") @PathVariable("brand") String brand,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit
    ) {
        log.info("Processing get products by brand name request for {} brand", brand);
        DefaultResponse response = iProductService.getProductsByBrandName(brand, limit, offset);
        if (!response.isSuccess()) {
            return ResponseEntity.status(response.getHttpStatus().get()).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
