package com.ecommerce.microservices.merchant_service.service;

import com.ecommerce.microservices.merchant_service.dto.RegisterMerchantDTO;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;

public interface IAuthService {
    public DefaultResponse registerUser(RegisterMerchantDTO registerMerchantDTO);
}
