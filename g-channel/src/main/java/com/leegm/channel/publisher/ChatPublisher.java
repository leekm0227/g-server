package com.leegm.channel.publisher;

import com.leegm.common.protocol.Chat;
import com.leegm.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChatPublisher {

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

    public Flux<Message> subscribe() {
        return chatFlux.map(chat -> Message.getRootAsMessage(ByteBuffer.wrap(chat.getByteBuffer().array())));
    }
}
