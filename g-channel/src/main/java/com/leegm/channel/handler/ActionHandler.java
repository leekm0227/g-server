package com.leegm.channel.handler;

import com.leegm.channel.publisher.ZonePublisher;
import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActionHandler extends AbstractHandler<Action> {

    private static final Logger logger = LoggerFactory.getLogger(ActionHandler.class);

    @Autowired
    ZonePublisher zonePublisher;

    @Autowired
    Dispatcher dispatcher;

    @PostConstruct
    public void init() {
        cls = Action.class;
        dispatcher.register(Payload.Action, this);
    }

    @Override
    public Message handle(Context context, Action action) {
        zonePublisher.update(action.object());
        return response(context, Result.SUCCESS);
    }
}
