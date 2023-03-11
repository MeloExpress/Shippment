package com.example.MeloExpress.Shippment.service;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CollectService {

    private CollectRepository collectRepository;

    @Autowired
    public CollectService(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    public Collect createCollect(String customerCode, LocalDateTime startTime, LocalDateTime endTime) {
        Collect collect = new Collect();
        collect.setCustomerCode(customerCode);
        collect.setStartTime(startTime);
        collect.setEndTime(endTime);
        return collectRepository.save(collect);
    }
}