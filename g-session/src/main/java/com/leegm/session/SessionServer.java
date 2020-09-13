package com.leegm.session;

import com.leegm.common.protocol.Message;
import com.leegm.session.client.ChannelClient;
import com.leegm.session.util.ConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.UnicastProcessor;

@Component
public class SessionServer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SessionServer.class);
    UnicastProcessor<Message> sendPublisher;

    @Autowired
    ConnManager connManager;

    @Autowired
    ChannelClient channelClient;

    @Override
    public void run(ApplicationArguments args) {
//        channelClient.conn().subscribe();

//        TcpServer.create()
//                .option(ChannelOption.SO_LINGER, 0)
//                .option(ChannelOption.SO_REUSEADDR, true)
//                .option(ChannelOption.TCP_NODELAY, true)
//                .port(9999)
//                .doOnConnection(conn -> {
//                    conn.addHandler(new Decoder());
//                    connManager.onConnect(conn);
//                })
//                .handle((inbound, outbound) -> outbound.sendByteArray(inbound.receiveObject()
//                        .ofType(Message.class)
//                        .map(dispatcher::handle)
//                        .flatMap(channelClient::send)
//                ))
//                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }
}

