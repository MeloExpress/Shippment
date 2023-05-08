package com.example.MeloExpress.Shippment.dto;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectStates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record CollectDetailsDTO(
        Long collectId,
        UUID customerCode,
        Long collectAddressId,
        CollectStates collectStates,
        String startTime,
        String endTime
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public CollectDetailsDTO(Collect collect) {
        this(
                collect.getCollectId(),
                collect.getCustomerCode(),
                collect.getCollectAddress().getCollectAddressId(),
                collect.getCollectState(),
                collect.getStartTime().format(formatter),
                collect.getEndTime().format(formatter)
        );
    }

    public LocalDateTime getStartTimeAsLocalDateTime() {
        return LocalDateTime.parse(startTime, formatter);
    }

    public LocalDateTime getEndTimeAsLocalDateTime() {
        return LocalDateTime.parse(endTime, formatter);
    }
}
