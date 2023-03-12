package com.example.MeloExpress.Shippment.dto;

public record CollectCreateDTO (
        String customerCode,
        String collectAddressId,
        String startTime,
        String endTime
){
}
