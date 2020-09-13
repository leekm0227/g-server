package com.leegm.common.handler;

public interface Handler<T> {

    byte[] handle(T message);
}
