package com.leegm.common.handler;

import com.leegm.common.protocol.Context;

public interface Handler<T> {

    byte[] handle(Context context, T message);
}
