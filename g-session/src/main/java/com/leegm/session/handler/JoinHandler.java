package com.leegm.session.handler;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Dispatcher;
import com.leegm.common.util.MessageConverter;
import com.leegm.session.publisher.ActionPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JoinHandler extends AbstractHandler<Join> {

    private static final Logger logger = LoggerFactory.getLogger(JoinHandler.class);

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ActionPublisher actionPublisher;

    @PostConstruct
    public void init() {
        cls = Join.class;
        dispatcher.register(Payload.Join, this);
    }


    @Override
    public Message handle(Context context, Join join) {
        long objectId = actionPublisher.join(join.userId(), join.name());
        return MessageConverter.toJoin(context, objectId, actionPublisher.getObjects());
    }
}
