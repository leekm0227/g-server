package com.leegm.channel;

import com.leegm.channel.publisher.ChatPublisher;
import com.leegm.channel.publisher.ZonePublisher;
import com.leegm.common.protocol.Message;
import com.leegm.common.util.Dispatcher;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

@Component
public class ChannelServer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChannelServer.class);

    @Autowired
    ProtocolEncoder protocolEncoder;

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    ZonePublisher zonePublisher;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
//                .runOn(LoopResources.create("channel-loop", 4, 8, true))
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_LINGER, 0)
                .port(50000)
                .metrics(true)
                .doOnConnection(connection -> {
                    connection.addHandler(new ProtocolDecoder());
                    connection.addHandler(protocolEncoder);
                })
                .handle((inbound, outbound) -> outbound.sendObject(inbound.receiveObject()
                        .ofType(Message.class)
//                        .log("channel server")
                        .map(dispatcher::handle)
                        .mergeWith(chatPublisher.subscribe())
                        .mergeWith(zonePublisher.subscribe())
                ))
                .bindNow(Duration.ofSeconds(30));
    }
}

