package com.ecommerce.microservices.product_service.service;

import com.ecommerce.microservices.product_service.dto.ProductDTO;
import com.ecommerce.microservices.product_service.entity.ProductEntity;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface IProductService {

    DefaultResponse addProduct(@Valid ProductDTO productEntity);

    Page<ProductDTO> getProductsOfMerchantByMerchantId(String merchantId, int page, int size);
}
