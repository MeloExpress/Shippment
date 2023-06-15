package com.example.MeloExpress.Shippment.Scheduler;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import com.example.MeloExpress.Shippment.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class VerifyCollectStatesAtrasadasUseCase implements VerifyCollectStatesUseCase {

    private final CollectRepository collectRepository;

    @Autowired
    public VerifyCollectStatesAtrasadasUseCase(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    @Override
    @Scheduled(fixedDelay = 60000) // Executa a cada 1 minuto
    public void execute() {
        List<CollectStates> states = List.of(CollectStates.AGENDADA);
        for (CollectStates state : states) {
            List<Collect> collectList = collectRepository.findByCollectState(state);
            LocalDateTime now = LocalDateTime.now();

            for (Collect collect : collectList) {
                if (collect.getEndTime().isBefore(now)) {
                    collect.setCollectState(CollectStates.ATRASADA);
                    collectRepository.save(collect);
                }
            }
        }
    }
}