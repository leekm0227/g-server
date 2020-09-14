package com.leegm.common.handler;

import com.google.flatbuffers.Table;
import com.leegm.common.protocol.Message;
import com.leegm.common.util.Converter;

@SuppressWarnings("unchecked")
public abstract class AbstractHandler<T extends Table> implements Handler<T> {

    public Class<T> cls;

    public byte[] handle(Message message) {
        try {
            return handle((T) message.payload(cls.getDeclaredConstructor().newInstance()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success();
    }

    public byte[] success() {
        return Converter.toSuccess();
    }
}
