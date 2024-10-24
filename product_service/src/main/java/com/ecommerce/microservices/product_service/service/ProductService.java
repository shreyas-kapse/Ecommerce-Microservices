package com.ecommerce.microservices.product_service.service;

import com.ecommerce.microservices.product_service.config.ModelMapperConfig;
import com.ecommerce.microservices.product_service.dto.ProductDTO;
import com.ecommerce.microservices.product_service.entity.ProductEntity;
import com.ecommerce.microservices.product_service.repository.ProductRepository;
import com.ecommerce.microservices.product_service.utils.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public Page<ProductDTO> getProductsOfMerchantByMerchantId(String merchantId, int page, int size) {
        try {
            log.info("Processing get all product request for merchant with id {}", merchantId);
            Pageable pageable = PageRequest.of(page, size);
            log.info("Successfully processed get all product request for merchant with id {}", merchantId);
            Page<ProductEntity> productEntities = productRepository.findAllByMerchantId(pageable, UUID.fromString(merchantId));
            return productEntities.map(productEntity -> mapper.modelMapper().map(productEntity,ProductDTO.class));
        } catch (Exception e) {
            log.error("Error occurred while processing get all product by merchant id request with error {} ", e.getMessage());
        }
        return null;
    }
}
