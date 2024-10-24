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

import java.util.Optional;

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
            ProductEntity product = mapper.modelMapper().map(productEntity,ProductEntity.class);
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
}
