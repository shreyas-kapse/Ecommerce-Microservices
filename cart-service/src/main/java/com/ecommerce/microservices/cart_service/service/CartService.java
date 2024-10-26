package com.ecommerce.microservices.cart_service.service;

import com.ecommerce.microservices.cart_service.client.ProductClient;
import com.ecommerce.microservices.cart_service.dto.ProductDTO;
import com.ecommerce.microservices.cart_service.entity.CartEntity;
import com.ecommerce.microservices.cart_service.entity.CartItem;
import com.ecommerce.microservices.cart_service.repository.CartItemRepository;
import com.ecommerce.microservices.cart_service.repository.CartRepository;
import com.ecommerce.microservices.cart_service.utils.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public DefaultResponse addProductToCart(String productId, String userId, int quantity) {
        try {
            log.info("Processing add product to cart request with product id {} for user with id {}", productId, userId);

            Optional<CartEntity> cartEntityOptional = cartRepository.findByUserId(UUID.fromString(userId));
            CartEntity cart;

            cart = cartEntityOptional.orElseGet(() -> createNewCart(UUID.fromString(userId)));
            if (cart == null) {
                DefaultResponse.builder()
                        .success(false)
                        .message("Error occurred while removing product")
                        .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                        .build();
            }
            ProductDTO product = productClient.getProductsById(productId);

            if (product == null) {
                return DefaultResponse.builder()
                        .success(false)
                        .message("Product not found")
                        .httpStatus(Optional.of(HttpStatus.NO_CONTENT))
                        .build();
            }
            Optional<CartItem> existingItem = cart.getCartItems()
                    .stream()
                    .filter(item -> item.getProductId().equals(UUID.fromString(productId)))
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItem cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);

                log.info("Request is already fulfilled to add product to cart with product id {} for user with id {}", productId, userId);
                return DefaultResponse.builder()
                        .success(true)
                        .message("Product added successfully")
                        .httpStatus(Optional.of(HttpStatus.CREATED))
                        .build();
            }

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(UUID.fromString(productId));
            newItem.setQuantity(quantity);
            newItem.setPrice(BigDecimal.valueOf(product.getPrice()));
            cart.getCartItems().add(newItem);

            cartRepository.save(cart);

            log.info("Successfully processed request to add product to cart with product id {} for user with id {}", productId, userId);
            return DefaultResponse.builder()
                    .success(true)
                    .message("Product added successfully")
                    .httpStatus(Optional.of(HttpStatus.CREATED))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while processing request to add product to cart with error {} ", e.getMessage());
            return DefaultResponse.builder()
                    .success(false)
                    .message("Error occurred while adding product")
                    .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    public CartEntity createNewCart(UUID userId) {
        try {
            log.info("Processing create new cart request for user with id {}", userId);
            CartEntity cart = new CartEntity();
            cart.setUserId(userId);

            log.info("Successfully processed create cart request for user with id {}", userId);
            return cartRepository.save(cart);
        } catch (Exception exception) {
            log.error("Error occurred while processing create cart request for user id {}, error {} ", userId, exception.getMessage());
        }
        return null;
    }
}
