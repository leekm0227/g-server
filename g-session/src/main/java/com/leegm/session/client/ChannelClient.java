package com.leegm.session.client;

import com.leegm.session.publisher.ChannelPublisher;
import com.leegm.session.publisher.SessionPublisher;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.netty.resources.ConnectionProvider;
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
        ConnectionProvider provider =  ConnectionProvider.builder("session").build();

        TcpClient.create(provider)
                .host(host)
                .port(port)
                .option(ChannelOption.TCP_NODELAY, true)
                .handle((in, out) -> {
                    in.receive().asByteArray().log("channel client").subscribe(bytes -> sessionPublisher.onNext(bytes));
                    return out.sendByteArray(channelPublisher.subscribe());
                })
                .connectNow();
    }
}
