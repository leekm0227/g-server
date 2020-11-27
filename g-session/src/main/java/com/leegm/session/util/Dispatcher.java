package com.leegm.session.util;

import com.leegm.common.protocol.Message;
import com.leegm.session.handler.AbstractHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashMap;

@Component
public class Dispatcher {
    private final HashMap<Byte, AbstractHandler<?>> handlers = new HashMap();

    public void register(byte payloadType, AbstractHandler<?> handler) {
        this.handlers.put(payloadType, handler);
    }

    public Flux<Message> handle(Flux<Message> request) {
        return request.flatMap(message -> {
            try {
                if (message.payloadType() == 0) {
                    return Flux.empty();
                } else {
                    AbstractHandler<?> handler = this.handlers.get(message.payloadType());
                    return handler == null ? Flux.empty() : handler.handle(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Flux.empty();
            }
        });
    }
}
