package com.ecommerce.microservices.merchant_service.service;

import com.ecommerce.microservices.merchant_service.dto.MerchantDTO;

public interface IMerchantService {
    public MerchantDTO getMerchantByCompanyName(String companyName);
}
