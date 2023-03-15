package com.example.MeloExpress.Shippment.dto;

import com.example.MeloExpress.Shippment.domain.Collect;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record CollectResponseDTO(
        Long collectId,
        Long collectAddressId,
        UUID customerCode,
        UUID addressCode,
        String startTime,
        String endTime
) {
    public CollectResponseDTO(Collect collect) {
        this(
                collect.getCollectId(),
                collect.getCollectAddress().getCollectAddressId(),
                collect.getCustomerCode(),
                collect.getCollectAddress().getAddressCode(),
                collect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                collect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        );
    }
}
