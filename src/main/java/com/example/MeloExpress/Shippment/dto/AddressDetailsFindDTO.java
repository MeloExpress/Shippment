package com.example.MeloExpress.Shippment.dto;

import java.util.UUID;

public record AddressDetailsFindDTO(
        Long addressId,
        UUID addressCode,
        String zipCode,
        String street,
        String number,
        String complements,
        String district,
        String city,
        String state,
        String pointReference) {
}
