package com.leegm.common.util;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.Message;

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

    public byte[] handle(byte[] bytes) {
        try {
            Message message = Message.getRootAsMessage(ByteBuffer.wrap(bytes));
            AbstractHandler<?> handler = handlers.get(message.payloadType());

            if (handler == null) {
                throw new RuntimeException();
            }

            return handler.handle(message);
        } catch (Exception e) {
            return Converter.toSuccess();
        }
    }
}
