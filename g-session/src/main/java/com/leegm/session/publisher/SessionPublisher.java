package com.leegm.session.publisher;

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
    public UnicastProcessor<byte[]> sessionPublisher;
    public Flux<byte[]> sessionFlux;

    @PostConstruct
    public void init() {
        sessionPublisher = UnicastProcessor.create();
        sessionFlux = sessionPublisher.replay(1).autoConnect(0);
    }

    public void onNext(byte[] bytes) {
        sessionPublisher.onNext(bytes);
    }

    public Flux<byte[]> subscribe() {
        return sessionFlux;
    }
}
