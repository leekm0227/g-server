package com.leegm.session.publisher;

import com.leegm.common.model.ObjectBean;
import com.leegm.common.protocol.Action;
import com.leegm.common.protocol.Context;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ActionPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ActionPublisher.class);
    public UnicastProcessor<Action> actionPublisher;
    public Flux<Action> actionFlux;

    private final ConcurrentHashMap<String, ObjectBean> objectMap = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong();
    private final Random random = new Random();
    private final int width = 32;
    private final int height = 32;
    private final int[] field = new int[width * height];

    @PostConstruct
    public void init() {
        actionPublisher = UnicastProcessor.create();
        actionFlux = actionPublisher.replay(1).autoConnect(0);

        // init map
        for (int i = 0; i < (width * height) / 20; i++) {
            field[random.nextInt(width * height) - 1] = 1;
        }
    }

    public void onNext(Context context, Action action) {
        // valid pos
        if (validPos(action.object().direction().x(), action.object().direction().y())) {
            return;
        }

        // check collide object

        // update
        if (objectMap.containsKey(context.userId()) && objectMap.get(context.userId()).getObjectId() == action.object().objectId()) {
            objectMap.get(context.userId()).update(action.object());
        }

        actionPublisher.onNext(action);
    }

    public long join(String userId, String name) {
        if (objectMap.containsKey(userId)) {
            return objectMap.get(userId).getObjectId();
        }

        int i;
        do {
            i = random.nextInt(width * height) - 1;
        } while (field[i] != 0);

        long objectId = nextId.incrementAndGet();
        objectMap.put(userId, new ObjectBean(name, objectId, Type.PLAYER, new float[]{i % height, Math.floorDiv(i, height), 0}));
        return objectId;
    }

    public ObjectBean[] getObjects() {
        return objectMap.values().toArray(new ObjectBean[0]);
    }

    public boolean leave(String userId) {
        if (objectMap.containsKey(userId)) {
            objectMap.remove(userId);
            return true;
        }

        return false;
    }

    public Flux<Message> subscribe() {
        return actionFlux.map(action -> Message.getRootAsMessage(ByteBuffer.wrap(action.getByteBuffer().array())));
    }

    private boolean validPos(float x, float y) {
        return field[(int) (y * height + x)] == 1;
    }


}
