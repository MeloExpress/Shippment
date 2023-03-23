package com.example.MeloExpress.Shippment.dto;

import java.util.UUID;

public record CollectResponseWithCustomerDTO(
        Long collectId,
        Long collectAddressId,
        UUID customerCode,
        UUID addressCode,
        String startTime,
        String endTime,
        CustomerDetailsFindDTO customerDetails,
        AddressDetailsFindDTO addressDetailsFindDTO
) {
}
