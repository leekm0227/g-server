package com.leegm.session.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;

@Component
public class ChannelPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ChannelPublisher.class);
    public UnicastProcessor<byte[]> channelPublisher;
    public Flux<byte[]> channelFlux;

    @PostConstruct
    public void init() {
        channelPublisher = UnicastProcessor.create();
        channelFlux = channelPublisher.replay(1).autoConnect(0);
    }

    public void onNext(byte[] bytes) {
        channelPublisher.onNext(bytes);
    }

    public Flux<byte[]> subscribe() {
        return channelFlux;
    }
}
