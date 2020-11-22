package com.leegm.channel.publisher;

import com.leegm.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.util.context.Context;

import javax.annotation.PostConstruct;
import java.util.function.LongConsumer;

@Component
public class PosPublisher {

    private static final Logger logger = LoggerFactory.getLogger(PosPublisher.class);
    ConnectableFlux<Message> flux;

    @PostConstruct
    public void init() {
    }

    public Flux<Message> subscribe() {
        return flux.replay(1).autoConnect(0);
    }

}
