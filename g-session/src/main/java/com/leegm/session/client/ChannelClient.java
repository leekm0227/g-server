package com.leegm.session.client;

import com.leegm.common.protocol.Message;
import com.leegm.session.util.Decoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;

@Component
public class ChannelClient {

    private String host;
    private int port;
    UnicastProcessor<Message> publisher2;

    @PostConstruct
    public void init() {
        UnicastProcessor<Message> publisher = UnicastProcessor.create();
        Flux<Message> flux = publisher.replay(1).autoConnect();

        publisher2 = UnicastProcessor.create();
        Flux<Message> flux2 = publisher2.replay(1).autoConnect();

        host = "127.0.0.1";
        port = 50000;
        TcpClient.create()
                .host(host)
                .port(port)
                .doOnConnected(connection -> connection.addHandler(new Decoder()))
                .handle((in, out) -> {
                    in.receiveObject().ofType(Message.class)
                            .subscribe(message -> {
                                System.out.println("receive message type :" + message.payloadType());
                                publisher.onNext(message);
                            });

                    return out.sendByteArray(flux2.map(message -> {
                        System.out.println("send message type : " + message.payloadType());
                        return message.getByteBuffer().array();
                    }));
                })
                .connectNow();
    }

    public void send(Message message) {
        publisher2.onNext(message);
    }

//    public Flux<Message> conn() {
//        return connection.inbound().receiveObject().ofType(Message.class);
//    }
//
//    public Publisher<Void> send(byte[] bytes) {
//        return connection.outbound().sendByteArray(Mono.just(bytes));
//    }
}
