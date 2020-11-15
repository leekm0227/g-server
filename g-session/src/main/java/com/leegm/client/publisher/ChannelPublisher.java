package com.leegm.client.publisher;

import com.leegm.common.protocol.Message;
import com.leegm.common.util.ProtocolDecoder;
import com.leegm.common.util.ProtocolEncoder;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ChannelPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ChannelPublisher.class);
    List<UnicastProcessor<Message>> clients = new ArrayList<>();
    private String host;
    private int port;
    private int size = 8;
    private Random random;

    @Autowired
    ProtocolEncoder protocolEncoder;

    @PostConstruct
    public void init() {
        random = new Random();
        host = "127.0.0.1";
        port = 50000;

        for (int i = 0; i < size; i++) {
            UnicastProcessor<Message> channelPublisher = UnicastProcessor.create();
            Flux<Message> channelFlux = channelPublisher.replay(1).autoConnect(0);
            clients.add(channelPublisher);

            TcpClient.create(ConnectionProvider.builder("session").build())
                    .host(host)
                    .port(port)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .doOnConnected(connection -> {
                        connection.addHandler(new ProtocolDecoder());
                        connection.addHandler(protocolEncoder);
                    })
                    .handle((in, out) -> {
                        in.receive().subscribe();
                        return out.sendObject(channelFlux.log("channel send"));
                    })
                    .connectNow(Duration.ofSeconds(30));
        }
    }

    public void onNext(byte[] bytes) {
        clients.get(random.nextInt(size)).onNext(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
    }
}
