package com.leegm.session.publisher;

import com.leegm.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;

@Component
public class ChannelPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ChannelPublisher.class);
    public UnicastProcessor<Message> channelPublisher;
    public Flux<Message> channelFlux;

    @PostConstruct
    public void init() {
        channelPublisher = UnicastProcessor.create();
        channelFlux = channelPublisher.replay(1).autoConnect(0);
    }

    public void onNext(Message message) {
        channelPublisher.onNext(message);
    }

    public void onNext(byte[] bytes) {
        channelPublisher.onNext(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
    }

    public Flux<Message> subscribe() {
        return channelFlux.map(message -> message);
    }
}
