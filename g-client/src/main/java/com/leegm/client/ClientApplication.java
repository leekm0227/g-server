package com.leegm.client;

import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Payload;
import com.leegm.common.util.Converter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientApplication {

    private static final List<Client> clients = new ArrayList<>();
    private static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        init();
        start();
    }

    public static void init() throws InterruptedException {
        for (int i = 0; i < 500; i++) {
            clients.add(new Client("127.0.0.1", 40000, ClientApplication::receive));
        }

        System.out.println("clients count : " + clients.size());
        Thread.sleep(3000);
    }

    public static void start() {
        for (; ; ) {
            clients.forEach(client -> {
                client.send(Message.getRootAsMessage(ByteBuffer.wrap(Converter.toChat("testcid", "testoid", "content"))).getByteBuffer().array());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void receive(Message message) {
        if (message.payloadType() == Payload.Chat) {
            System.out.println("msg" + count.addAndGet(1) + " type : " + message.payloadType());
        }
    }
}