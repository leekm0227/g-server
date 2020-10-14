package com.leegm.channel.publisher;

import com.leegm.common.model.ObjectBean;
import com.leegm.common.model.ZoneBean;
import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.Type;
import com.leegm.common.util.Const;
import com.leegm.common.util.MessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class ZonePublisher {

    private static final Logger logger = LoggerFactory.getLogger(ZonePublisher.class);
    private ZoneBean currentZone;
    private UnicastProcessor<ZoneBean> zonePublisher;
    private Flux<ZoneBean> zoneFlux;

    @PostConstruct
    public void init() {
        currentZone = new ZoneBean();
        zonePublisher = UnicastProcessor.create();
        zoneFlux = zonePublisher.replay(1).autoConnect(0);
    }

    public void onNext(Object object) {
        Optional<ObjectBean> objectBean = currentZone.getObjects().stream()
                .filter(x -> x.getObjectId() == object.objectId())
                .findFirst();

        if (objectBean.isPresent()) {
            objectBean.get().update(object);
            zonePublisher.onNext(currentZone);
        } else {
            currentZone.getObjects().add(new ObjectBean("name", (int) object.objectId(), Type.PLAYER, new float[]{0, 0, 0}));
        }


//        zonePublisher.onNext(currentZone);
//        log();
    }

    public void onNext(ZoneBean zone) {
        zonePublisher.onNext(zone);
    }

    public Flux<Message> subscribe() {
        return zoneFlux.map(MessageConverter::toZone);
    }

    private void log() {
        int height = 40;
        int width = 70;
        int[][] array = new int[height][width];

        currentZone.getObjects().forEach(objectBean -> {
            array[(int) objectBean.getPosition(Const.POS_Y)][(int) objectBean.getPosition(Const.POS_X)] = objectBean.getObjectId();
        });

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int index = array[i][j];
                String tmp = "  ";

                if (array[i][j] > 0) {
                    if (index < 10) {
                        tmp = " " + index;
                    } else {
                        tmp = "" + index;
                    }
                }

                sb.append("[").append(tmp).append("]");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
