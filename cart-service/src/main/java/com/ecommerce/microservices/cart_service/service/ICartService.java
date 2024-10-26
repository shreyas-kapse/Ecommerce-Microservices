package com.ecommerce.microservices.cart_service.service;

import com.ecommerce.microservices.cart_service.utils.DefaultResponse;

public interface ICartService {
    DefaultResponse addProductToCart(String productId, String userId, int quantity);
}
