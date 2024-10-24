package com.ecommerce.microservices.merchant_service.service;

import com.ecommerce.microservices.merchant_service.dto.MerchantDTO;
import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import jakarta.validation.Valid;

public interface IMerchantService {
    public MerchantDTO getMerchantByCompanyName(String companyName);

    MerchantDTO getMerchantByEmail(String email);

    DefaultResponse addProduct(@Valid ProductDTO productEntity);

    DefaultResponse getProductsOfMerchantByMerchantId(String merchantId, int offset, int limit);
}
