package com.leegm.client;


import com.leegm.common.protocol.Message;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class Client {

    private final UnicastProcessor<Message> publisher;
    private final Flux<Message> flux;
    private Message lastMessage;
    public int index;
    public int receiveCount = 0;
    public CountDownLatch latch;


    public Client(int index, AtomicInteger count, ProtocolEncoder protocolEncoder, CountDownLatch latch, String host, int port, BiConsumer<Client, Message> receive) {
        this.index = index;
        this.latch = latch;
        publisher = UnicastProcessor.create();
        flux = publisher.replay(1).autoConnect(0);

        TcpClient.create(ConnectionProvider.builder("fixed").maxConnections(5000).build())
                .host(host)
                .port(port)
                .doOnConnected(connection -> {
                    connection.addHandler(new ProtocolDecoder());
                    connection.addHandler(protocolEncoder);
                    System.out.println("client count : " + count.incrementAndGet());
                })
                .doOnDisconnected(connection -> {
                    System.out.println("client count : " + count.decrementAndGet());
                })
                .handle((in, out) -> {
                    in.receiveObject().ofType(Message.class).subscribe(message -> {
                        lastMessage = message;
                        receive.accept(this, lastMessage);
                    });
                    return out.sendObject(flux);
                })
                .connectNow();
    }

    public void send(Message message) {
        publisher.onNext(message);
    }

    public Message getLastMessage() {
        return lastMessage;
    }
}
