package com.ecommerce.microservices.merchant_service.controller;

import com.ecommerce.microservices.merchant_service.dto.RegisterMerchantDTO;
import com.ecommerce.microservices.merchant_service.service.IAuthService;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/test")
    @Operation(summary = "Test merchant ", description = "Endpoint for merchant testing")
    public String testEndpoint(){
        return "Testing";
    }

    @PostMapping("/register")
    @Operation(summary = "Register on platform", description = "Register API")
    public ResponseEntity<DefaultResponse> register(@Valid @RequestBody RegisterMerchantDTO registerMerchantDTO, BindingResult result) {
        DefaultResponse response;
        log.info("Processing register user request for the {}",registerMerchantDTO.getEmail());
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
}
