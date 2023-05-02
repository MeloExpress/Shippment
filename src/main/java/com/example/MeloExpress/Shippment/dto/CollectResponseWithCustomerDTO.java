package com.example.MeloExpress.Shippment.dto;

import com.example.MeloExpress.Shippment.domain.CollectStates;

import java.util.UUID;

public record CollectResponseWithCustomerDTO(
        Long collectId,
        Long collectAddressId,
        UUID customerCode,
        UUID addressCode,
        String startTime,
        String endTime,
        String collectState,
        CustomerDetailsFindDTO customerData,
        AddressDetailsFindDTO addressData
) {
}
