package com.leegm.session.publisher;

import com.leegm.common.model.ObjectBean;
import com.leegm.common.model.ZoneBean;
import com.leegm.common.protocol.Action;
import com.leegm.common.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActionPublisher {

    private static final Logger logger = LoggerFactory.getLogger(ActionPublisher.class);
    public UnicastProcessor<Action> actionPublisher;
    public Flux<Action> actionFlux;
    private ConcurrentHashMap<Long, ObjectBean> map;

    @PostConstruct
    private void init() {
        map = new ConcurrentHashMap<>();
        actionPublisher = UnicastProcessor.create();
        actionFlux = actionPublisher.replay(1).autoConnect(0);
    }

    public void onNext(Action action) {
        // valid pos
        Random random = new Random();
        if (random.nextInt(10) == 1) return;

        // update pos
        map.getOrDefault(action.object().objectId(), new ObjectBean(action.object().name(), action.object().objectId(), action.object().type())).update(action.object());
        logger.info("zone objs size : {}", map.mappingCount());

        actionPublisher.onNext(action);
    }

    public Flux<Message> subscribe(){
        return actionFlux.map(action -> Message.getRootAsMessage(ByteBuffer.wrap(action.getByteBuffer().array())));
    }


//    public static void main(String[] args) {
//        List<Integer> tests = new ArrayList<>();
//        Flux<Integer> tmp = Flux.range(1001, 2000);
//        Flux.range(1, 1000).flatMap(integer -> {
//            tests.add(integer);
//            return tmp;
//        }).subscribe(integer -> System.out.println("test1 : "+integer));
//
//        tmp.subscribe(integer -> System.out.println("test2 : "+integer));
//        System.out.println(tests.size());
//    }

//    public static void main(String[] args) {
//        ZoneBean zone = new ZoneBean();
//        List<Action> actionList = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < 2000; i++) {
//            int idx = random.nextInt(1000);
//            int posX = random.nextInt(100);
//            // set action
//            FlatBufferBuilder builder = new FlatBufferBuilder();
//            int userIdOffset = builder.createString("userId");
//            int sessionIdOffset = builder.createString("sessionId");
//            int objectNameOffset = builder.createString("objectName");
//            int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
//            com.leegm.common.protocol.Object.startObject(builder);
//            com.leegm.common.protocol.Object.addObjectId(builder, idx);
//            com.leegm.common.protocol.Object.addName(builder, objectNameOffset);
//            com.leegm.common.protocol.Object.addDirection(builder, Vec3.createVec3(builder, posX, 0, 0));
//            int objectOffset = Object.endObject(builder);
//            int actionOffset = Action.createAction(builder, objectOffset);
//            builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, Result.SUCCESS, Payload.Action, actionOffset));
//            Message message = Message.getRootAsMessage(ByteBuffer.wrap(builder.sizedByteArray()));
//            Action action = (Action) message.payload(new Action());
//            actionList.add(action);
//        }
//
//        Flux.fromStream(actionList.stream()).parallel().log("test").flatMap(action -> {
//            zone.getObjects().parallelStream().filter(x -> x.getObjectId() == action.object().objectId()).findFirst().orElseGet(() -> {
//                ObjectBean object = new ObjectBean(action.object().name(), action.object().objectId(), action.object().type());
//                zone.getObjects().add(object);
//                return object;
//            }).update(action.object());
//
//            System.out.println(zone.getObjects().size());
//            return Flux.just(MessageConverter.toZone(zone));
//        }).subscribe(message -> {
//            Zone test = (Zone) message.payload(new Zone());
//            for (int i = 0; i <test.objectsLength() ; i++) {
//                System.out.println("obj id : "+test.objects(i).objectId()+", pos x : " + test.objects(i).position().x());
//            }
//            System.out.println("-------------------------------------------------------");
//        });
//    }
}


