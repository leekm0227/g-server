package com.leegm.client;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.*;
import com.leegm.common.util.ProtocolEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ClientTask {

    @Autowired
    ProtocolEncoder protocolEncoder;

    private static final List<Client> clients = new ArrayList<>();
    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private static final AtomicInteger receiveCount = new AtomicInteger(0);
    private static final int clientSize = 2000;
    private static final int rateMillis = 50;
    private static final int port = 40000; // 40000 = session, 50000 = channel

    @PostConstruct
    void init() {
        for (int i = 0; i < clientSize; i++) {
            clients.add(new Client(i, clientCount, protocolEncoder, "127.0.0.1", port, ClientTask::receive));
        }
    }

    @Scheduled(fixedRate = rateMillis)
    public void task() {
        clients.forEach(ClientTask::sendAction);
    }

    private static void receive(Client client, Message message) {
        System.out.println("receive count : " + receiveCount.incrementAndGet());

        switch (message.payloadType()) {
            case Payload.Chat:
            case Payload.Zone:
                break;
        }
    }

    public static void sendAction(Client client) {
        String userId = "client" + client.index;
        String sessionId = "session" + client.index;
        Message message = client.getLastMessage();
        Random r = new Random();
        int height = 156;
        int width = 280;
        float dirX = 0;
        float dirY = 0;
        float dirZ = 0;

        if (message != null) {
            try {
                Zone zone = (Zone) message.payload(new Zone());
                if (zone != null) {
                    for (int i = 0; i < zone.objectsLength(); i++) {
                        if (zone.objects(i).objectId() == client.index) {
//                                    System.out.println("client" + index + " pos : " + zone.objects(i).position().x() + ", " + zone.objects(i).position().y() + ", " + zone.objects(i).position().z());
                            dirX = zone.objects(i).position().x();
                            dirY = zone.objects(i).position().y();
                            dirZ = zone.objects(i).position().z();
                        }
                    }
                }
            } catch (Exception e) {

            }
        }

        if (r.nextBoolean()) {
            if (r.nextBoolean()) {
                dirX = Math.min(width, dirX + 1f);
            } else {
                dirX = Math.max(0, dirX - 1f);
            }
        } else {
            if (r.nextBoolean()) {
                dirY = Math.min(height, dirY + 1f);
            } else {
                dirY = Math.max(0, dirY - 1f);
            }
        }

        FlatBufferBuilder builder = new FlatBufferBuilder();
        int userIdOffset = builder.createString(userId);
        int sessionIdOffset = builder.createString(sessionId);
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        com.leegm.common.protocol.Object.startObject(builder);
        com.leegm.common.protocol.Object.addObjectId(builder, client.index);
        com.leegm.common.protocol.Object.addDirection(builder, Vec3.createVec3(builder, dirX, dirY, dirZ));
        int objectOffset = Object.endObject(builder);
        int actionOffset = Action.createAction(builder, objectOffset);
        builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, Result.SUCCESS, Payload.Action, actionOffset));
        client.send(Message.getRootAsMessage(ByteBuffer.wrap(builder.sizedByteArray())));
    }
}
