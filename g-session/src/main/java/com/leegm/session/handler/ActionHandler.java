package com.leegm.session.handler;

import com.leegm.common.protocol.*;
import com.leegm.session.publisher.ActionPublisher;
import com.leegm.session.util.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Component
public class ActionHandler extends AbstractHandler<Action> {

    private static final Logger logger = LoggerFactory.getLogger(ActionHandler.class);

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ActionPublisher actionPublisher;

    @PostConstruct
    public void init() {
        cls = Action.class;
        dispatcher.register(Payload.Action, this);
    }

    @Override
    public Flux<Message> handle(Action action) {
        actionPublisher.onNext(action);
        return Flux.empty();
    }
}
