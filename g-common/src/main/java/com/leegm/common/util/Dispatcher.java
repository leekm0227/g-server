package com.leegm.common.util;

import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.Message;
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

    public byte[] handle(byte[] bytes) {
        try {
            Message message = Message.getRootAsMessage(ByteBuffer.wrap(bytes));
            AbstractHandler<?> handler = handlers.get(message.payloadType());

            if (handler == null) {
                return Converter.response(null, Result.ERROR_HANDLER_NOT_FOUND);
            }

            return handler.handle(message);
        } catch (IndexOutOfBoundsException e) {
            return Converter.response(null, Result.ERROR_INVALID_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            return Converter.response(null, Result.ERROR_RUNTIME);
        }
    }
}
