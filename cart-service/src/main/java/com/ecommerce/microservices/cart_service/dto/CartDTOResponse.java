package com.ecommerce.microservices.cart_service.dto;

import com.ecommerce.microservices.cart_service.utils.DefaultResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CartDTOResponse {
    private DefaultResponse response;
    private Optional<CartDTO> cart;
}
