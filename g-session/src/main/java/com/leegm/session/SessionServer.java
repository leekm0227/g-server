package com.leegm.session;

import com.leegm.common.util.Dispatcher;
import com.leegm.session.publisher.SessionPublisher;
import com.leegm.session.util.ConnManager;
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
                .option(ChannelOption.SO_LINGER, 0)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .port(40000)
                .handle((inbound, outbound) -> outbound.sendByteArray(inbound.receive()
                        .asByteArray()
                        .log("session server")
                        .map(dispatcher::handle)
                        .mergeWith(sessionPublisher.subscribe())
                ))
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

