package com.ecommerce.microservices.merchant_service.dto;

import com.ecommerce.microservices.merchant_service.entity.MerchantEntity;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MerchantDTO {
    private DefaultResponse response;
    private Optional<MerchantEntity> merchant;
}