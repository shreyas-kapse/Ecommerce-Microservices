package com.ecommerce.microservices.merchant_service.repository;

import com.ecommerce.microservices.merchant_service.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, UUID> {
    Optional<MerchantEntity> findUserByEmail(String email);

    Optional<MerchantEntity> findByCompanyName(String companyName);

    Optional<MerchantEntity> findByEmail(String email);
}
