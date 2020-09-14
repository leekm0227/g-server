package com.leegm.session.publisher;

import com.leegm.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SessionPublisher {

    private AtomicInteger count = new AtomicInteger(0);
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

    public Flux<byte[]> subscribe() {
        return sessionFlux.map(message -> message.getByteBuffer().array());
    }
}
