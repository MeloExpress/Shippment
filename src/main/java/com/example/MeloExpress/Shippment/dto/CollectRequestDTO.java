package com.example.MeloExpress.Shippment.dto;

import com.example.MeloExpress.Shippment.domain.Collect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record CollectRequestDTO(
        Long collectId,
        String customerCode,
        String startTime,
        String endTime
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CollectRequestDTO(Collect collect) {
        this(
                collect.getCollectId(),
                collect.getCustomerCode(),
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
