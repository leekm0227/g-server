package com.leegm.client;


import com.leegm.common.protocol.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class Client {

    private final UnicastProcessor<byte[]> publisher;
    private final Flux<byte[]> flux;
    private Message lastMessage;

    public Client(String host, int port, Consumer<Message> receive) {
        publisher = UnicastProcessor.create();
        flux = publisher.replay(1).autoConnect(0);
        ConnectionProvider provider = ConnectionProvider.builder("fixed").maxConnections(5000).build();

        TcpClient.create(provider)
                .host(host)
                .port(port)
                .handle((in, out) -> {
                    in.receive().asByteArray().subscribe(bytes -> {
                        try {
                            lastMessage = Message.getRootAsMessage(ByteBuffer.wrap(bytes));
                            receive.accept(lastMessage);
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

    public Message getLastMessage() {
        return lastMessage;
    }
}
