package com.ecommerce.microservices.merchant_service.repository;

import com.ecommerce.microservices.merchant_service.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository extends JpaRepository<MerchantEntity, UUID> {
    Optional<MerchantEntity> findUserByEmail(String email);

    Optional<MerchantEntity> findByCompanyName(String companyName);
}
