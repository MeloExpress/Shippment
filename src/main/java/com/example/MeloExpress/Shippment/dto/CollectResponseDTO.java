package com.example.MeloExpress.Shippment.dto;

import com.example.MeloExpress.Shippment.domain.Collect;

import java.time.format.DateTimeFormatter;

public record CollectResponseDTO(
        Long collectId,
        String customerCode,
        Long collectAddressId,
        String startTime,
        String endTime
) {
    public CollectResponseDTO(Collect collect) {
        this(
                collect.getCollectId(),
                collect.getCustomerCode(),
                collect.getCollectAddress().getCollectAddressId(),
                collect.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                collect.getEndTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
