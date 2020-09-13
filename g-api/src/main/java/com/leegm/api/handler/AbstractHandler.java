package com.leegm.api.handler;

import com.leegm.api.Dispatcher;
import com.leegm.api.model.RequestBean;
import com.leegm.api.util.FbConverter;
import com.google.flatbuffers.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")
public abstract class AbstractHandler<T extends Table> implements Handler<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHandler.class);

    @Autowired
    Dispatcher demoHandlerFactory;

    Class<T> cls;

    public byte[] handle(RequestBean req) {
        try {
            return handle(
                    req.getSid(),
                    (T) req.getMessage().payload(cls.newInstance()),
                    req.getMessage().method()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empty();
    }

    public byte[] empty() {
        return FbConverter.toEmpty();
    }
}
