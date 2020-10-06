package com.leegm.client;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.*;
import com.leegm.common.util.Converter;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ClientApplication {

    private static final List<Client> clients = new ArrayList<>();
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final int clientSize = 3000;
    private static final int sleepTime = 10;
    private static final int port = 40000; // 40000 = session, 50000 = channel

    public static void main(String[] args) throws InterruptedException {
        init(ClientApplication::receive);
        sendAction();
    }

    public static void sendAction() {
        for (; ; ) {
            clients.forEach(client -> {
                //create move message
                int index = clients.indexOf(client);

//                if (index % 2 == 0) return;

                String userId = "client" + index;
                String sessionId = "session" + index;
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
                                if (zone.objects(i).objectId() == index) {
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
                Object.startObject(builder);
                Object.addObjectId(builder, index);
                Object.addDirection(builder, Vec3.createVec3(builder, dirX, dirY, dirZ));
                int objectOffset = Object.endObject(builder);
                int actionOffset = Action.createAction(builder, objectOffset);
                builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, Result.SUCCESS, Payload.Action, actionOffset));
                client.send(Message.getRootAsMessage(ByteBuffer.wrap(builder.sizedByteArray())).getByteBuffer().array());

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void sendChat() {
        for (; ; ) {
            clients.forEach(client -> {
                int userId = clients.indexOf(client);

                client.send(Message.getRootAsMessage(ByteBuffer.wrap(Converter.toChat("topic", "userid", "name", "content"))).getByteBuffer().array());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void receive(Message message) {
        switch (message.payloadType()) {
            case Payload.Chat:
                System.out.println("msg" + count.addAndGet(1) + " type : " + message.payloadType());
            case Payload.Zone:
//                System.out.println("msg" + count.addAndGet(1) + " type : " + message.payloadType());
                break;
        }
    }

    public static void init(Consumer<Message> receive) throws InterruptedException {
        for (int i = 0; i < clientSize; i++) {
            clients.add(new Client("127.0.0.1", port, receive));
        }

        System.out.println("clients count : " + clients.size());
        Thread.sleep(3000);
    }

    public static void httpServerTest() {
        HttpServer.create().port(8080).route(routes -> routes.get("/test/{param}", (request, reponse) -> reponse.sendString(request.receive().asString()))).bindNow();

        HttpClient.create().port(8080).get().uri("/test/aaa").responseContent().aggregate().asString().block();
    }
}