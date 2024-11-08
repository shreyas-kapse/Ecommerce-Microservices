package com.ecommerce.microservices.product_service.repository;

import com.ecommerce.microservices.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Override
    List<ProductEntity> findAll();

    @Query(value = "SELECT *FROM product_entity WHERE merchant_id=:merchantId limit :limit offset :offset", nativeQuery = true)
    List<ProductEntity> findAllByMerchantId(@Param("merchantId") UUID merchantId, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT COUNT(*) FROM product_entity WHERE merchant_id = :merchantId", nativeQuery = true)
    int countByMerchantId(@Param("merchantId") UUID merchantId);

    @Query(value = "SELECT *FROM product_entity WHERE brand = :brand limit :limit offset :offset", nativeQuery = true)
    Optional<List<ProductEntity>> findAllByBrand(@Param("brand") String brand, @Param("limit") int limit, @Param("offset") int offset);

    @Query(value = "SELECT *FROM product_entity WHERE category = :category limit :limit offset :offset", nativeQuery = true)
    Optional<List<ProductEntity>> findAllByCategory(@Param("category") String categoryName, @Param("limit") int limit, @Param("offset") int offset);
}
