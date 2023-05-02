package com.example.MeloExpress.Shippment.service;

import com.example.MeloExpress.Shippment.domain.Collect;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import com.example.MeloExpress.Shippment.repository.CollectRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CollectStateService {

    private final CollectRepository collectRepository;

    public CollectStateService(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    public void updateStates(Long collectId, CollectStates newState) {
        Collect scheduling = collectRepository.findById(collectId)
                .orElseThrow(() -> new NoSuchElementException("Agendamento não encontrado com o ID: " + collectId));
        scheduling.setCollectState(newState);
        collectRepository.save(scheduling);
    }
}

