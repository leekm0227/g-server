package com.leegm.common.handler;

import com.google.flatbuffers.Table;
import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Result;
import com.leegm.common.util.Converter;

@SuppressWarnings("unchecked")
public abstract class AbstractHandler<T extends Table> implements Handler<T> {

    public Class<T> cls;

    public byte[] handle(Message message) {
        try {
            return handle(message.context(), (T) message.payload(cls.getDeclaredConstructor().newInstance()));
        } catch (Exception e) {
            e.printStackTrace();
            return response(message.context(), Result.ERROR_CLS_NOT_FOUND);
        }
    }

    public byte[] response(Context context, byte result) {
        return Converter.response(context, result);
    }
}
