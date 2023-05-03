package com.example.MeloExpress.Shippment.domain;

import com.example.MeloExpress.Shippment.dto.CollectResponseWithCustomerDTO;
import com.example.MeloExpress.Shippment.service.CollectStateService;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

public class CollectStateListener extends StateMachineListenerAdapter<CollectStates, CollectEvents> {
    private final CollectStateService collectStateService;

    private CollectResponseWithCustomerDTO collectResponseWithCustomerDTO;

    public CollectStateListener(CollectStateService collectStateService) {
        this.collectStateService = collectStateService;
    }
    @Override
    public void transition(Transition<CollectStates, CollectEvents> transition) {
        if (transition.getTarget().getId() != null) {
            Long collectId = collectResponseWithCustomerDTO.collectId();
            CollectStates state = transition.getTarget().getId();
            collectStateService.updateStates(collectId, state);
        }
    }
}