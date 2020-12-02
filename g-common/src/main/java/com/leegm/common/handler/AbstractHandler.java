package com.leegm.common.handler;

import com.google.flatbuffers.Table;
import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Result;
import com.leegm.common.util.MessageConverter;

@SuppressWarnings("unchecked")
public abstract class AbstractHandler<T extends Table> implements Handler<T> {

    public Class<T> cls;

    public Message handle(Message message) {
        try {
            return handle(message.context(), (T) message.payload(cls.getDeclaredConstructor().newInstance()));
        } catch (Exception e) {
            e.printStackTrace();
            return response(message.context(), Result.ERROR_CLS_NOT_FOUND);
        }
    }

    public Message response(Context context, byte result) {
        return MessageConverter.response(context, result);
    }

    public Message empty() {
        return MessageConverter.empty();
    }
}
