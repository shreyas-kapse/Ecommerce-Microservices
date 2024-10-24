package com.ecommerce.microservices.merchant_service.controller;

import com.ecommerce.microservices.merchant_service.client.ProductClient;
import com.ecommerce.microservices.merchant_service.dto.MerchantDTO;
import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.ecommerce.microservices.merchant_service.dto.RegisterMerchantDTO;
import com.ecommerce.microservices.merchant_service.service.IAuthService;
import com.ecommerce.microservices.merchant_service.service.IMerchantService;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/merchant")
@Slf4j
@Tag(name = "Merchant", description = "Operations related to merchant ")
public class MerchantController {

    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private IMerchantService iMerchantService;

    @GetMapping("/test")
    @Operation(summary = "Test merchant ", description = "Endpoint for merchant testing")
    public String testEndpoint() {
        return productClient.productTest();
    }

    @PostMapping("/register")
    @Operation(summary = "Register on platform", description = "Register API")
    public ResponseEntity<DefaultResponse> register(@Valid @RequestBody RegisterMerchantDTO registerMerchantDTO, BindingResult result) {
        DefaultResponse response;
        log.info("Processing register user request for the {}", registerMerchantDTO.getEmail());
        if (result.hasErrors()) {
            Map<String, String> error = new HashMap<>();
            result.getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));
            response = DefaultResponse.builder()
                    .success(false)
                    .message("Validation failed")
                    .errors(Optional.of(error))
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        response = iAuthService.registerUser(registerMerchantDTO);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{companyName}")
    @Operation(summary = "Get merchant by company name")
    public ResponseEntity<MerchantDTO> getMerchantByCompanyName(
            @Parameter(description = "Get merchant by company name", example = "SK Enterprises") @PathVariable("companyName") String companyName
    ) {
        log.info("Processing get merchant by company name request for the merchant with company name {}", companyName);
        MerchantDTO response = iMerchantService.getMerchantByCompanyName(companyName);
        return response.getResponse().isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(response.getResponse().getHttpStatus().get()).body(response);
    }

    @GetMapping
    @Operation(summary = "Get merchant by email")
    public ResponseEntity<MerchantDTO> getMerchantByEmail(
            @Parameter(description = "Get merchant by email", example = "example@example.com") @RequestParam("email") String email
    ) {
        log.info("Processing get merchant by email request for the merchant with email {}", email);
        MerchantDTO response = iMerchantService.getMerchantByEmail(email);
        return response.getResponse().isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(response.getResponse().getHttpStatus().get()).body(response);
    }

    @PostMapping("/product/add-product")
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
        response = iMerchantService.addProduct(productEntity);

        return !response.isSuccess() ? ResponseEntity.status(response.getHttpStatus().get()).body(response) : ResponseEntity.ok(response);
    }

}
