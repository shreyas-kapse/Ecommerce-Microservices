package com.ecommerce.microservices.merchant_service.service;

import com.ecommerce.microservices.merchant_service.client.ProductClient;
import com.ecommerce.microservices.merchant_service.dto.MerchantDTO;
import com.ecommerce.microservices.merchant_service.dto.ProductDTO;
import com.ecommerce.microservices.merchant_service.entity.MerchantEntity;
import com.ecommerce.microservices.merchant_service.repository.MerchantRepository;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MerchantService implements IMerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public MerchantDTO getMerchantByCompanyName(String companyName) {
        try {
            log.info("Processing get merchant request for company name {}", companyName);
            Optional<MerchantEntity> merchant = merchantRepository.findByCompanyName(companyName);

            if (merchant.isPresent() && merchant.get().getCompanyName().equalsIgnoreCase(companyName)) {
                log.info("Successfully processed get merchant request for merchant with company name {}", companyName);
                return MerchantDTO.builder()
                        .response(DefaultResponse.builder()
                                .success(true)
                                .build())
                        .merchant(merchant)
                        .build();
            }
            return MerchantDTO.builder()
                    .response(DefaultResponse.builder()
                            .success(false)
                            .message("No merchant found")
                            .httpStatus(Optional.of(HttpStatus.NO_CONTENT))
                            .build())
                    .build();
        } catch (Exception exception) {
            log.error("Error occurred while processing get merchant request with error {} ", exception.getMessage());
            return MerchantDTO.builder()
                    .response(DefaultResponse.builder()
                            .success(false)
                            .message("Error occurred while fetching merchant")
                            .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                            .build())
                    .build();
        }
    }

    @Override
    public MerchantDTO getMerchantByEmail(String email) {
        try {
            log.info("Processing get merchant request for email {}", email);
            Optional<MerchantEntity> merchant = merchantRepository.findByEmail(email);

            if (merchant.isPresent() && merchant.get().getEmail().equalsIgnoreCase(email)) {
                log.info("Successfully processed get merchant request for merchant with email {}", email);
                return MerchantDTO.builder()
                        .response(DefaultResponse.builder()
                                .success(true)
                                .build())
                        .merchant(merchant)
                        .build();
            }
            return MerchantDTO.builder()
                    .response(DefaultResponse.builder()
                            .success(false)
                            .message("No merchant found")
                            .httpStatus(Optional.of(HttpStatus.NO_CONTENT))
                            .build())
                    .build();
        } catch (Exception exception) {
            log.error("Error occurred while processing get merchant by email request with error {} ", exception.getMessage());
            return MerchantDTO.builder()
                    .response(DefaultResponse.builder()
                            .success(false)
                            .message("Error occurred while fetching merchant")
                            .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                            .build())
                    .build();
        }
    }

    @Override
    public DefaultResponse addProduct(ProductDTO productEntity) {
        return productClient.addProduct(productEntity);
    }

    @Override
    public DefaultResponse getProductsOfMerchantByMerchantId(String merchantId, int offset, int limit) {
        return productClient.getProductsOfMerchantByMerchantId(merchantId,offset,limit);
    }
}
