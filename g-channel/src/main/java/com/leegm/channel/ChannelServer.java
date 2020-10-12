package com.leegm.channel;

import com.leegm.channel.publisher.ChatPublisher;
import com.leegm.channel.publisher.ZonePublisher;
import com.leegm.common.util.Dispatcher;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

@Component
public class ChannelServer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChannelServer.class);

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    ZonePublisher zonePublisher;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
                .option(ChannelOption.SO_REUSEADDR, true)
                .port(50000)
                .metrics(true)
                .handle((inbound, outbound) -> outbound.sendByteArray(inbound.receive()
                        .asByteArray()
                        .log("channel server")
                        .map(dispatcher::handle)
                        .mergeWith(chatPublisher.subscribe(inbound.hashCode()))
                        .mergeWith(zonePublisher.subscribe())
                ))
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

