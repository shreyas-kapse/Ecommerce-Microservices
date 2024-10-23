package com.ecommerce.microservices.merchant_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @ManyToOne
    @JsonBackReference
    private MerchantEntity merchant;

    private String description;
}
