package com.leegm.client;


import com.leegm.common.protocol.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.tcp.TcpClient;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class Client {

    private final UnicastProcessor<byte[]> publisher;
    private final Flux<byte[]> flux;

    public Client(String host, int port, Consumer<Message> receive) {
        publisher = UnicastProcessor.create();
        flux = publisher.replay(1).autoConnect(0);

        TcpClient.create()
                .host(host)
                .port(port)
                .handle((in, out) -> {
                    in.receive().asByteArray().subscribe(bytes -> {
                        try {
                            receive.accept(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
                        } catch (Exception ignored) {
                        }
                    });
                    return out.sendByteArray(flux);
                })
                .connectNow();
    }

    public void send(byte[] bytes) {
        publisher.onNext(bytes);
    }
}
