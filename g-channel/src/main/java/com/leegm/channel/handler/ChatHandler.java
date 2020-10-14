package com.leegm.channel.handler;

import com.leegm.channel.publisher.ChatPublisher;
import com.leegm.common.handler.AbstractHandler;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Dispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChatHandler extends AbstractHandler<Chat> {

    private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    Dispatcher dispatcher;

    @PostConstruct
    public void init() {
        cls = Chat.class;
        dispatcher.register(Payload.Chat, this);
    }

    @Override
    public Message handle(Context context, Chat chat) {
        chatPublisher.onNext(chat);
        return response(context, Result.SUCCESS);
    }
}
