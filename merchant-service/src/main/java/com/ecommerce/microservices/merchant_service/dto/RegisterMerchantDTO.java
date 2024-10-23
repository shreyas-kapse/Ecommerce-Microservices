package com.ecommerce.microservices.merchant_service.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterMerchantDTO {


    @NotBlank(message = "Email is required")
    @Size(max = 25, message = "Email can not be more than 25 characters")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(max = 20, message = "First name can not be more than 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 20, message = "Last name can not be more than 20 characters")
    private String lastName;

    @NotNull(message = "Merchant phone is required")
    @Digits(integer = 10, fraction = 0, message = "Phone number must be at most 10 digits")
    private Long merchantPhoneNo;

    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "Company name is required")
    @Size(max = 30, message = "Company name can be more than 30 characters")
    private String companyName;


    @NotBlank(message = "City name is required")
    private String city;

    @NotBlank(message = "State name is required")
    private String state;

    @NotBlank(message = "Pin code is required")
    private String pinCode;

    @NotBlank(message = "Country name is required")
    private String country;
}

