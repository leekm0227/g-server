package com.leegm.api.handler;

public interface Handler<T> {

    byte[] handle(String sid, T request, byte method);
}
