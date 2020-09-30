package com.leegm.session.handler;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.Action;
import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Payload;
import com.leegm.common.protocol.Result;
import com.leegm.common.util.Dispatcher;
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
    ChannelPublisher channelPublisher;

    @PostConstruct
    public void init() {
        cls = Action.class;
        dispatcher.register(Payload.Action, this);
    }

    @Override
    public byte[] handle(Context context, Action action) {
        channelPublisher.onNext(action.getByteBuffer().array());
        return response(context, Result.SUCCESS);
    }

}
