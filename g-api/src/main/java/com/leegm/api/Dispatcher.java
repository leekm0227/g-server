package com.leegm.api;

import com.leegm.api.handler.AbstractHandler;
import com.leegm.api.model.RequestBean;
import com.leegm.api.publisher.ChatPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private HashMap<Byte, AbstractHandler<?>> handlers;

    @Autowired
    ChatPublisher chatPublisher;

    @PostConstruct
    public void init() {
        handlers = new HashMap<>();
    }

    public void register(byte bodyType, AbstractHandler<?> handler) {
        handlers.put(bodyType, handler);
    }

    byte[] handle(RequestBean req) {
        AbstractHandler<?> handler = handlers.get(req.getMessage().payloadType());

        if (handler == null) {
            throw new RuntimeException();
        }

        return handler.handle(req);
    }
}
