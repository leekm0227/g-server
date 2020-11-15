package com.leegm.channel;

import com.leegm.channel.publisher.ChatPublisher;
import com.leegm.channel.publisher.ZonePublisher;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

@Component
public class ChannelServerOutbound implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChannelServerOutbound.class);

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    ZonePublisher zonePublisher;

    @Autowired
    ProtocolEncoder protocolEncoder;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_LINGER, 0)
                .port(51000)
                .metrics(true)
                .doOnConnection(connection -> {
                    connection.addHandler(new ProtocolDecoder());
                    connection.addHandler(protocolEncoder);
                })
                .handle((inbound, outbound) -> {
                    inbound.receive().subscribe();
                    return outbound.sendObject(zonePublisher.subscribe().mergeWith(chatPublisher.subscribe()));
                })
                .bindNow(Duration.ofSeconds(30));
    }
}

