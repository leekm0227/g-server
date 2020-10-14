package com.leegm.common.handler;

import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Message;

public interface Handler<T> {

    Message handle(Context context, T message);
}
