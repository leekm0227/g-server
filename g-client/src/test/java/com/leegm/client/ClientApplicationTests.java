package com.leegm.client;


import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.protocol.*;
import com.leegm.common.protocol.Object;
import com.leegm.common.util.ProtocolEncoder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class ClientApplicationTests {

    @Autowired
    ProtocolEncoder protocolEncoder;

    private static final Logger logger = LoggerFactory.getLogger(ClientApplicationTests.class);
    private static final List<Client> clients = new ArrayList<>();
    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private static final AtomicInteger receiveCount = new AtomicInteger(0);
    private static final int clientSize = 500;
    private static final int rateMillis = 100;
    private static final int port = 40000; // 40000 = session, 50000 = channel
    private final CountDownLatch latch = new CountDownLatch(clientSize);

    @Test
    void test() throws InterruptedException {
        for (int i = 0; i < clientSize; i++) {
            clients.add(new Client(i, clientCount, protocolEncoder, latch, "127.0.0.1", port, ClientApplicationTests::callBack));
        }

        long start = System.currentTimeMillis();
        logger.info("start : {}", start);
        clients.forEach(ClientApplicationTests::send);

        latch.await();
        long end = System.currentTimeMillis();
        logger.info("finish : {}", end);
        logger.info("result : {}", (end - start));
    }

    private static void send(Client client) {
        String userId = "client" + client.index;
        String sessionId = "session" + client.index;
        int x = client.receiveCount++;

        FlatBufferBuilder builder = new FlatBufferBuilder();
        int userIdOffset = builder.createString(userId);
        int sessionIdOffset = builder.createString(sessionId);
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        com.leegm.common.protocol.Object.startObject(builder);
        com.leegm.common.protocol.Object.addObjectId(builder, client.index);
        com.leegm.common.protocol.Object.addDirection(builder, Vec3.createVec3(builder, x, 0, 0));
        int objectOffset = Object.endObject(builder);
        int actionOffset = Action.createAction(builder, objectOffset);
        builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, Result.SUCCESS, Payload.Action, actionOffset));
        client.send(Message.getRootAsMessage(ByteBuffer.wrap(builder.sizedByteArray())));
    }

    private static void callBack(Client client, Message message) {
        if(client.receiveCount >= 1000){
            client.latch.countDown();
        }

        try {
            Thread.sleep(rateMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        send(client);
    }
}
