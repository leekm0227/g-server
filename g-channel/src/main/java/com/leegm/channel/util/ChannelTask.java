package com.leegm.channel.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.channel.publisher.ChatPublisher;
import com.leegm.common.protocol.Chat;
import com.leegm.common.protocol.Message;
import com.leegm.common.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;


@Component
public class ChannelTask {

    @Autowired
    ChatPublisher chatPublisher;

    @Scheduled(fixedRate = 1000)
    public void task() {
        Message msg = Message.getRootAsMessage(ByteBuffer.wrap(Converter.toChat("testcid", "testoid", "content")));
        Chat chat = (Chat) msg.payload(new Chat());
//        chatPublisher.onNext(chat);
    }
}



