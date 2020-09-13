package com.leegm.api;

import com.leegm.api.model.RequestBean;
import com.leegm.api.publisher.ChatPublisher;
import com.leegm.api.publisher.FieldPublisher;
import com.leegm.api.util.ChannelManager;
import com.leegm.api.util.FbDecoder;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.netty.tcp.TcpServer;

import java.time.Duration;

@Component
@Profile("!api")
public class DemoServer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DemoServer.class);

    @Autowired
    Dispatcher dispatcher;

    @Autowired
    ChannelManager channelManager;

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    FieldPublisher fieldPublisher;

    @Override
    public void run(ApplicationArguments args) {
        TcpServer.create()
                .option(ChannelOption.SO_LINGER, 0)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .port(9999)
                .doOnConnection(conn -> {
                    channelManager.onConnect(conn);
                    conn.addHandler(new FbDecoder());
                })
                .handle((inbound, outbound) -> outbound.sendByteArray(inbound.receiveObject()
                        .ofType(RequestBean.class)
                        .map(requestBean -> dispatcher.handle(requestBean))
                        .mergeWith(chatPublisher.subscribe(inbound.hashCode()))
                        .mergeWith(fieldPublisher.subscribe())
                ))
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

