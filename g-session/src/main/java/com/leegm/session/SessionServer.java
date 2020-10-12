package com.leegm.session;

import com.leegm.common.util.Dispatcher;
import com.leegm.session.publisher.SessionPublisher;
import com.leegm.session.util.ConnManager;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

@Component
public class SessionServer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SessionServer.class);

    @Autowired
    ConnManager connManager;

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    SessionPublisher sessionPublisher;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
//                .runOn(LoopResources.create("session-loop", 2, 8, true))
                .option(ChannelOption.SO_REUSEADDR, true)
                .port(40000)
                .metrics(true)
                .handle((inbound, outbound) -> outbound.sendByteArray(inbound.receive()
                        .asByteArray()
                        .log("session server")
                        .map(dispatcher::handle)
                        .mergeWith(sessionPublisher.subscribe())
                ))
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

