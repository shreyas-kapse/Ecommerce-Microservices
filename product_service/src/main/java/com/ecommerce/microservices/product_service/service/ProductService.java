package com.ecommerce.microservices.product_service.service;

import com.ecommerce.microservices.product_service.config.ModelMapperConfig;
import com.ecommerce.microservices.product_service.dto.ProductDTO;
import com.ecommerce.microservices.product_service.entity.ProductEntity;
import com.ecommerce.microservices.product_service.repository.ProductRepository;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapperConfig mapper;

    @Override
    public DefaultResponse addProduct(ProductDTO productEntity) {
        try {

            log.info("Processing add product request for merchant with id {}", productEntity.getMerchantId());
            ProductEntity product = mapper.modelMapper().map(productEntity, ProductEntity.class);
            productRepository.save(product);

            log.info("Successfully processed add product request for merchant with id {}", productEntity.getMerchantId());
            return DefaultResponse.builder()
                    .success(true)
                    .message("Product added successfully")
                    .build();
        } catch (Exception exception) {
            log.error("Error occurred while processing add product request with error {} ", exception.getMessage());
            return DefaultResponse.builder()
                    .success(false)
                    .message("Error occurred while adding new product")
                    .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }

    @Override
    public DefaultResponse getProductsOfMerchantByMerchantId(String merchantId, int offset, int limit) {
        try {
            log.info("Processing get all product request for merchant with id {}", merchantId);
            log.info("Requested offset: {}, limit: {}", offset, limit);
            log.info("Successfully processed get all product request for merchant with id {}", merchantId);
            List<ProductEntity> productEntities = productRepository.findAllByMerchantId(UUID.fromString(merchantId), offset, limit);
            int totalRecords = productRepository.countByMerchantId(UUID.fromString(merchantId));
            int totalPages = (int) Math.ceil((double) totalRecords / limit);
            if (totalRecords == 0 || productEntities.isEmpty()) {
                return DefaultResponse.builder()
                        .success(true)
                        .totalProducts(Optional.of(totalRecords))
                        .totalPages(Optional.of(totalPages))
                        .message("No records found")
                        .httpStatus(Optional.of(HttpStatus.NO_CONTENT))
                        .build();
            }
            List<ProductDTO> productDTOs = productEntities.stream()
                    .map(productEntity -> mapper.modelMapper().map(productEntity, ProductDTO.class))
                    .collect(Collectors.toList());

            return DefaultResponse.builder()
                    .success(true)
                    .httpStatus(Optional.of(HttpStatus.OK))
                    .products(Optional.of(productDTOs))
                    .totalProducts(Optional.of(totalRecords))
                    .totalPages(Optional.of(totalPages))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while processing get all product by merchant id request with error {} ", e.getMessage());
            return DefaultResponse.builder()
                    .success(false)
                    .message("Error occurred while processing get all products of merchant request")
                    .httpStatus(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR))
                    .build();
        }
    }
}
