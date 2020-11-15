package com.leegm.channel.publisher;

import com.leegm.common.model.ObjectBean;
import com.leegm.common.model.ZoneBean;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.Type;
import com.leegm.common.util.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ZonePublisher {

    private static final Logger logger = LoggerFactory.getLogger(ZonePublisher.class);
    private ConcurrentLinkedQueue<ObjectBean> objectBeans;
    private UnicastProcessor<ZoneBean> zonePublisher;
    private Flux<ZoneBean> zoneFlux;

    @PostConstruct
    public void init() {
        objectBeans = new ConcurrentLinkedQueue<>();
        zonePublisher = UnicastProcessor.create();
        zoneFlux = zonePublisher.replay(1).autoConnect(0);
    }

    public void onNext(Object object) {
        Optional<ObjectBean> updated = objectBeans.stream()
                .filter(x -> x.getObjectId() == object.objectId())
                .map(targetBean -> {
                    logger.info("updated client{} object pos : {} -> {}",object.objectId(), targetBean.getPosition(), new float[]{object.direction().x(), object.direction().y(), object.direction().z()});
                    return targetBean.update(object);
                })
                .findFirst();

        if (!updated.isPresent()) {
            objectBeans.add(new ObjectBean("name", (int) object.objectId(), Type.PLAYER, new float[]{0, 0, 0}));
        }
    }

    public int onNext() {
        zonePublisher.onNext(new ZoneBean((objectBeans.toArray(new ObjectBean[0]))));
        return objectBeans.hashCode();
    }

    public Flux<Message> subscribe() {
        return zoneFlux.map(MessageConverter::toZone);
    }

    public boolean isUpdate(int hash){
        return objectBeans.hashCode() == hash;
    }
}
