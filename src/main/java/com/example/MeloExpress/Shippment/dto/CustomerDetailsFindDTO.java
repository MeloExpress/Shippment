package com.example.MeloExpress.Shippment.dto;

import java.util.UUID;

public record CustomerDetailsFindDTO(
        Long customerId,
        UUID customerCode,
        String companyName,
        String cnpj,
        String stateRegistration,
        String email, String phone,
        String responsible) {

}
