package com.example.MeloExpress.Shippment.service;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.MeloExpress.Shippment.domain.CollectStates.*;

@Service
public class ScheduleService {

    @Autowired
    private CollectService collectService;

    @Scheduled(fixedDelay = 60000) // Executa a cada 1 minutos
    public void verifyCollectsAgendadas() {
        List<Collect> collectList = collectService.findCollectsByStatus(AGENDADA);
        LocalDateTime now = LocalDateTime.now();

        for (Collect collect : collectList) {
            if (collect.getEndTime().isBefore(now)) {
                collect.setCollectState(ATRASADA);
                collectService.save(collect);
            }
        }
    }

    @Scheduled(fixedDelay = 60000) // Executa a cada 1 minutos
    public void verifyCollectsRoteirizadas() {
        List<Collect> collectList = collectService.findCollectsByStatus(ROTEIRIZADA);
        LocalDateTime now = LocalDateTime.now();

        for (Collect collect : collectList) {
            if (collect.getEndTime().isBefore(now)) {
                collect.setCollectState(ATRASADA);
                collectService.save(collect);
            }
        }
    }
}
