package com.ecommerce.microservices.cart_service.service;

import com.ecommerce.microservices.cart_service.dto.CartDTOResponse;
import com.ecommerce.microservices.cart_service.utils.DefaultResponse;

public interface ICartService {
    DefaultResponse addProductToCart(String productId, String userId, int quantity);

    CartDTOResponse getCart(String userId);

    DefaultResponse removeProductFromCart(String productId, String userId);
}
