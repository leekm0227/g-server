package com.leegm.client;

import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Payload;
import com.leegm.common.util.Converter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Task {

    private final List<Client> clients = new ArrayList<>();
    private final AtomicInteger count = new AtomicInteger(0);

    @PostConstruct
    public void init() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            clients.add(new Client("127.0.0.1", 40000, this::receive));
        }

        System.out.println("clients count : " + clients.size());
        Thread.sleep(10000);
    }

    @Scheduled(fixedRate = 100)
    public void task() {
        clients.forEach(client -> client.send(Message.getRootAsMessage(ByteBuffer.wrap(Converter.toChat("testcid", "testoid", "content"))).getByteBuffer().array()));
    }

    private void receive(Message message) {
        if (message.payloadType() == Payload.Chat) {
            System.out.println("msg" + count.addAndGet(1) + " type : " + message.payloadType());
        }
    }
}



