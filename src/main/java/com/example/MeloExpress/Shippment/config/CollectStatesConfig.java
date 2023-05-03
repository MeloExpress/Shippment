package com.example.MeloExpress.Shippment.config;

import com.example.MeloExpress.Shippment.domain.CollectEvents;
import com.example.MeloExpress.Shippment.domain.CollectStates;
import com.example.MeloExpress.Shippment.domain.CollectStateListener;
import com.example.MeloExpress.Shippment.service.CollectStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class CollectStatesConfig extends EnumStateMachineConfigurerAdapter<CollectStates, CollectEvents> {

    @Autowired
    private CollectStateService collectStateService;

    @Override
    public void configure(StateMachineStateConfigurer<CollectStates, CollectEvents> states) throws Exception {
        states
                .withStates()
                .initial(CollectStates.AGENDADA)
                .states(EnumSet.allOf(CollectStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CollectStates, CollectEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(CollectStates.AGENDADA)
                .target(CollectStates.ROTEIRIZADA)
                .event(CollectEvents.ROTEIRIZAR)
                .and()
                .withExternal()
                .source(CollectStates.AGENDADA)
                .target(CollectStates.CANCELADA)
                .event(CollectEvents.CANCELAR)
                .and()
                .withExternal()
                .source(CollectStates.AGENDADA)
                .target(CollectStates.ATRASADA)
                .event(CollectEvents.ATRASAR)
                .and()
                .withExternal()
                .source(CollectStates.ROTEIRIZADA)
                .target(CollectStates.CANCELADA)
                .event(CollectEvents.CANCELAR)
                .and()
                .withExternal()
                .source(CollectStates.ROTEIRIZADA)
                .target(CollectStates.ATRASADA)
                .event(CollectEvents.ATRASAR)
                .and()
                .withExternal()
                .source(CollectStates.ROTEIRIZADA)
                .target(CollectStates.REALIZADA)
                .event(CollectEvents.REALIZAR)
                .and()
                .withExternal()
                .source(CollectStates.ATRASADA)
                .target(CollectStates.CANCELADA)
                .event(CollectEvents.CANCELAR)
                .and()
                .withExternal()
                .source(CollectStates.ATRASADA)
                .target(CollectStates.ROTEIRIZADA)
                .event(CollectEvents.ROTEIRIZAR)
       ;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<CollectStates, CollectEvents> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new CollectStateListener(collectStateService));
    }
}
