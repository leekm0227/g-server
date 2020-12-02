package com.leegm.session.handler;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Dispatcher;
import com.leegm.session.publisher.ActionPublisher;
import com.leegm.session.publisher.ChannelPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Message handle(Context context, Action action) {
        // valid action

        actionPublisher.onNext(context, action);
        return response(context, Result.SUCCESS);
    }

}
