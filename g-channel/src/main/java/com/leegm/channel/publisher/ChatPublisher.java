package com.leegm.channel.publisher;

import com.leegm.common.protocol.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChatPublisher {

    private AtomicInteger count = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(ChatPublisher.class);
    public UnicastProcessor<Chat> chatPublisher;
    public Flux<Chat> chatFlux;

    @PostConstruct
    public void init() {
        chatPublisher = UnicastProcessor.create();
        chatFlux = chatPublisher.replay(1).autoConnect(0);
    }

    public void onNext(Chat chat) {
        chatPublisher.onNext(chat);
    }

    public Flux<byte[]> subscribe(int hashCode) {
        return chatFlux.map(chat -> chat.getByteBuffer().array());
    }
}
