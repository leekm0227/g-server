package com.leegm.session.client;

import com.leegm.session.publisher.ChannelPublisher;
import com.leegm.session.publisher.SessionPublisher;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class ChannelClient {

    @Autowired
    ChannelPublisher channelPublisher;

    @Autowired
    SessionPublisher sessionPublisher;

    private String host;
    private int port;

    @PostConstruct
    public void init() {
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            data.add("send string" + i);
        }

        HttpClient.create()
                .websocket()
                .uri("ws://localhost:8080/conn")
                .handle((inbound, outbound) -> {
                    inbound.receive().asString().subscribe(System.out::println);
                    return outbound.send(Flux.fromStream(data.stream()).map(s -> Unpooled.wrappedBuffer(s.getBytes()))).neverComplete();
                })
                .blockLast();
    }
}
