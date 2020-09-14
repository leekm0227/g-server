package com.leegm.session.client;

import com.leegm.common.protocol.Message;
import com.leegm.session.publisher.ChannelPublisher;
import com.leegm.session.publisher.SessionPublisher;
import com.leegm.session.util.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PostConstruct;

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
        host = "127.0.0.1";
        port = 50000;
        TcpClient.create()
                .host(host)
                .port(port)
                .doOnConnected(connection -> connection.addHandler(new Decoder()))
                .handle((in, out) -> {
                    in.receiveObject()
                            .log("channel client")
                            .ofType(Message.class)
                            .subscribe(message -> sessionPublisher.onNext(message));

                    return out.sendByteArray(channelPublisher.subscribe());
                })
                .connectNow();
    }
}
