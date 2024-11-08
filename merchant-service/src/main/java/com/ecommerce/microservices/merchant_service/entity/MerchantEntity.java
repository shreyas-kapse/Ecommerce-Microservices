package com.ecommerce.microservices.merchant_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class MerchantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID id;

    @NotBlank(message = "Password is required")
    @JsonIgnore
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

    @NotBlank(message = "Email is required")
    @Size(max = 25, message = "Email can not be more than 25 characters")
    private String email;

    @NotBlank(message = "Company name is required")
    @Size(max = 30, message = "Company name can be more than 30 characters")
    private String companyName;

    @NotBlank(message = "Office address is required")
    @Size(max = 30, message = "Office address must be at most 30 letters")
    private String officeAddress;

    @NotNull(message = "Pin code is required")
    @Digits(integer = 10, fraction = 0, message = "Pin code can not be more than 10 digits")
    private Long pinCode;

    @NotBlank(message = "City is required")
    @Size(max = 20, message = "City name can not be more than 20 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 10, message = "State name can not be more than 10 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 15, message = "Country name can not be more than 15 characters")
    private String country;

    private String accountStatus;
}
