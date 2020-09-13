package com.leegm.api.handler;

import com.leegm.api.flatbuffer.FbChat;
import com.leegm.api.flatbuffer.FbMethod;
import com.leegm.api.flatbuffer.FbPayload;
import com.leegm.api.publisher.ChatPublisher;
import com.leegm.api.util.ChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ChatHandler extends AbstractHandler<FbChat> {

    private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    @Autowired
    ChatPublisher chatPublisher;

    @Autowired
    ChannelManager channelManager;

    @PostConstruct
    public void init() {
        cls = FbChat.class;
        demoHandlerFactory.register(FbPayload.FbChat, this);
    }

    @Override
    public byte[] handle(String sid, FbChat chat, byte method) {
        switch (method) {
            case FbMethod.D:
                channelManager.leave(sid, chat.cid());
            case FbMethod.R:
                channelManager.join(sid, chat.cid());
            default:
                chatPublisher.onNext(chat);
        }

        return empty();
    }
}
