package com.leegm.session.client;

import com.leegm.common.protocol.Message;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import com.leegm.session.publisher.ChannelPublisher;
import com.leegm.session.publisher.SessionPublisher;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
public class ChannelClient {

    @Autowired
    ChannelPublisher channelPublisher;

    @Autowired
    SessionPublisher sessionPublisher;

    @Autowired
    ProtocolEncoder protocolEncoder;

    private String host;
    private int port;

    @PostConstruct
    public void init() {
        host = "127.0.0.1";
        port = 50000;

        TcpClient.create(ConnectionProvider.builder("session").build())
                .host(host)
                .port(port)
                .option(ChannelOption.TCP_NODELAY, true)
                .doOnConnected(connection -> {
                    connection.addHandler(new ProtocolDecoder());
                    connection.addHandler(protocolEncoder);
                })
                .handle((in, out) -> {
                    in.receiveObject().ofType(Message.class)
                            .log("channel client")
                            .subscribe(message -> sessionPublisher.onNext(message));
                    return out.sendObject(channelPublisher.subscribe());
                })
                .connectNow(Duration.ofSeconds(30));
    }
}
