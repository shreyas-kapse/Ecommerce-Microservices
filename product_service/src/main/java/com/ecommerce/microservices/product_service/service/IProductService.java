package com.ecommerce.microservices.product_service.service;

import com.ecommerce.microservices.product_service.dto.ProductDTO;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import jakarta.validation.Valid;

public interface IProductService {

    DefaultResponse addProduct(@Valid ProductDTO productEntity);

    DefaultResponse getProductsOfMerchantByMerchantId(String merchantId, int offset, int limit);

    ProductDTO getProductById(String id);
}
