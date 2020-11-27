package com.leegm.session.handler;

import com.google.flatbuffers.Table;
import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Message;
import reactor.core.publisher.Flux;

public interface Handler<T> {

    Flux<Message> handle(T message);
}
