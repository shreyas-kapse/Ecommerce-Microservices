package com.ecommerce.microservices.merchant_service.utils;

import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultResponse {
    private boolean success;
    private String message;

    @Builder.Default
    private Optional<Map<String, String>> errors = Optional.empty();

    @Builder.Default
    private Optional<Map<String, String>> data = Optional.empty();

    @JsonIgnore
    private Optional<HttpStatus> httpStatus;

    @Builder.Default
    private Optional<List<ProductDTO>> products = Optional.empty();

    private Optional<Integer> totalPages;

    private Optional<Integer> totalProducts;
}
