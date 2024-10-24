package com.ecommerce.microservices.product_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;

    @NotBlank(message = "Product name can not be blank")
    @Size(max = 20, min = 3, message = "Product name can not be less than 3 and more than 15 characters")
    private String productName;

    @NotNull(message = "Product price can not be null")
    @Digits(integer = 7, fraction = 2)
    @Min(value = 0, message = "Stock quantity can not be less than zero")
    private Long price;

    @NotNull(message = "Product quantity can not be null")
    @Digits(integer = 7, fraction = 0)
    private Long quantity;

    @NotBlank(message = "Product category can not be blank")
    @Size(min = 3, max = 20, message = "Product name can not be more than 15 characters")
    private String category;

    @NotBlank(message = "Brand name can not be blank")
    @Size(max = 25, min = 2, message = "Brand name can not be more than 25 characters")
    private String brand;

    @NotNull(message = "Available status is required")
    private Boolean available;

    @JsonIgnore
    private UUID merchantId;

    private String description;
}
