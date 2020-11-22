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
public class SessionPublisher {

    private static final Logger logger = LoggerFactory.getLogger(SessionPublisher.class);
    public UnicastProcessor<Message> sessionPublisher;
    public Flux<Message> sessionFlux;

    @PostConstruct
    public void init() {
        sessionPublisher = UnicastProcessor.create();
        sessionFlux = sessionPublisher.replay(1).autoConnect(0);
    }

    public void onNext(Message message) {
        sessionPublisher.onNext(message);
    }

    public void onNext(byte[] bytes) {
        sessionPublisher.onNext(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
    }

    public Flux<Message> subscribe() {
        return sessionFlux;
    }
}
