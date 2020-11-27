package com.leegm.session.handler;

import com.google.flatbuffers.Table;
import com.leegm.common.protocol.Message;
import reactor.core.publisher.Flux;

public abstract class AbstractHandler<T extends Table> implements Handler<T> {

    public Class<T> cls;

    public Flux<Message> handle(Message message) {
        try {
            T context = (T) message.payload(cls.newInstance());
            return this.handle(context);
        } catch (Exception e) {
            e.printStackTrace();
            return Flux.empty();
        }
    }
}
