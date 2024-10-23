package com.ecommerce.microservices.merchant_service.service;

import com.ecommerce.microservices.merchant_service.constants.AccountStatus;
import com.ecommerce.microservices.merchant_service.dto.RegisterMerchantDTO;
import com.ecommerce.microservices.merchant_service.entity.MerchantEntity;
import com.ecommerce.microservices.merchant_service.repository.MerchantRepository;
import com.ecommerce.microservices.merchant_service.utils.DefaultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService implements IAuthService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public DefaultResponse registerUser(RegisterMerchantDTO registerMerchantDTO) {
        try {
            log.info("Processing register user request");
            Optional<MerchantEntity> user = merchantRepository.findUserByEmail(registerMerchantDTO.getEmail());
            if (user.isPresent()) {
                return DefaultResponse.builder()
                        .success(false)
                        .message("User already exits with this email")
                        .build();
            }
            MerchantEntity userEntity = MerchantEntity.builder()
                    .firstName(registerMerchantDTO.getFirstName())
                    .lastName(registerMerchantDTO.getLastName())
                    .accountStatus(AccountStatus.UNVERIFIED.name())
                    .officeAddress(registerMerchantDTO.getAddressLine1() + registerMerchantDTO.getAddressLine2())
                    .city(registerMerchantDTO.getCity())
                    .pinCode(Long.valueOf(registerMerchantDTO.getPinCode()))
                    .country(registerMerchantDTO.getCountry())
                    .state(registerMerchantDTO.getState())
                    .merchantPhoneNo(registerMerchantDTO.getMerchantPhoneNo())
                    .email(registerMerchantDTO.getEmail())
                    .companyName(registerMerchantDTO.getCompanyName())
                    .password(new BCryptPasswordEncoder(12).encode(registerMerchantDTO.getPassword()))
                    .build();
            merchantRepository.save(userEntity);

            log.info("Successfully processed register user request for {}", registerMerchantDTO.getEmail());

            return DefaultResponse.builder()
                    .success(true)
                    .message("User register successfully")
                    .build();

        } catch (Exception exception) {
            log.error("Error occurred while processing register user request for {} user with error {}", registerMerchantDTO.getEmail(), exception.getMessage());
            return DefaultResponse.builder()
                    .success(false)
                    .message("Error occurred while registering the user")
                    .build();
        }
    }
}
