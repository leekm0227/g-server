package com.leegm.common.util;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Payload;
import com.leegm.common.protocol.Result;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class Dispatcher {

    private final HashMap<Byte, AbstractHandler<?>> handlers;

    public Dispatcher() {
        handlers = new HashMap<>();
    }

    public void register(byte payloadType, AbstractHandler<?> handler) {
        handlers.put(payloadType, handler);
    }

    public Message handle(Message message) {
        try {
            if(message.payloadType() == Payload.NONE){
                return MessageConverter.response(null, Result.ERROR_RUNTIME);
            }

            AbstractHandler<?> handler = handlers.get(message.payloadType());

            if (handler == null) {
                return MessageConverter.response(null, Result.ERROR_HANDLER_NOT_FOUND);
            }

            return handler.handle(message);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageConverter.response(null, Result.ERROR_RUNTIME);
        }
    }
}
