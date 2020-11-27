package com.leegm.session;

import com.leegm.common.protocol.Message;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import com.leegm.session.publisher.ActionPublisher;
import com.leegm.session.util.Dispatcher;
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
    ProtocolEncoder protocolEncoder;

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ActionPublisher actionPublisher;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
                .option(ChannelOption.SO_REUSEADDR, true)
                .port(40000)
                .metrics(true)
                .doOnConnection(connection -> {
                    connection.addHandler(new ProtocolDecoder());
                    connection.addHandler(protocolEncoder);
                })
                .handle((inbound, outbound) -> outbound.sendObject(dispatcher.handle(inbound.receiveObject().ofType(Message.class)).mergeWith(actionPublisher.subscribe())))
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

