package com.leegm.session.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.protocol.*;
import com.leegm.common.protocol.Object;
import com.leegm.session.publisher.ActionPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.Random;

//@Component
public class SessionTask {

    @Autowired
    ActionPublisher actionPublisher;
    Random random;

    @PostConstruct
    void init() {
        random = new Random();
    }

    @Scheduled(fixedRate = 10)
    public void task() {
        int idx = random.nextInt(10);
        int posX = random.nextInt(100);
        // set action
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int userIdOffset = builder.createString("userId");
        int sessionIdOffset = builder.createString("sessionId");
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        com.leegm.common.protocol.Object.startObject(builder);
        com.leegm.common.protocol.Object.addObjectId(builder, idx);
        com.leegm.common.protocol.Object.addDirection(builder, Vec3.createVec3(builder, posX, 0, 0));
        int objectOffset = Object.endObject(builder);
        int actionOffset = Action.createAction(builder, objectOffset);
        builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, Result.SUCCESS, Payload.Action, actionOffset));
        Message message = Message.getRootAsMessage(ByteBuffer.wrap(builder.sizedByteArray()));
        Action action = (Action) message.payload(new Action());

        actionPublisher.onNext(action);
    }
}



