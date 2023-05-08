package com.example.MeloExpress.Shippment.domain;

import com.example.MeloExpress.Shippment.dto.CollectDetailsDTO;
import com.example.MeloExpress.Shippment.service.CollectStateService;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

public class CollectStateListener extends StateMachineListenerAdapter<CollectStates, CollectEvents> {
    private final CollectStateService collectStateService;

    private CollectDetailsDTO collectDetailsDTO;

    public CollectStateListener(CollectStateService collectStateService) {
        this.collectStateService = collectStateService;
    }
    @Override
    public void transition(Transition<CollectStates, CollectEvents> transition) {
        if (transition.getTarget().getId() != null) {
            Long collectId = collectDetailsDTO.collectId();
            CollectStates state = transition.getTarget().getId();
            collectStateService.updateStates(collectId, state);
        }
    }
}