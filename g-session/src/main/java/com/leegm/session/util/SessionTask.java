package com.leegm.session.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Converter;
import com.leegm.session.client.ChannelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class SessionTask {

    @Autowired
    ChannelClient channelClient;

    @Scheduled(fixedRate = 1000)
    public void task() {
        Message msg = Message.getRootAsMessage(ByteBuffer.wrap(Converter.toChat("testcid", "testoid", "content")));
        channelClient.send(msg);
    }
}



