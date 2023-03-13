package com.example.MeloExpress.Shippment.dto;

import java.util.UUID;

public record CollectCreateDTO (
        UUID customerCode,
        String collectAddressId,
        UUID addressCode,
        String startTime,
        String endTime
) {}
