package com.leegm.api;

import com.leegm.api.flatbuffer.FbType;
import com.leegm.api.model.FieldBean;
import com.leegm.api.publisher.FieldPublisher;
import com.leegm.api.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DemoTask {

    @Autowired
    FieldPublisher fieldPublisher;

    @Scheduled(fixedRate = 100)
    public void task() {
        FieldBean fieldBean = fieldPublisher.getLastField();
        fieldBean.getObjects().stream()
                .filter(x -> x.getType() == FbType.N)
                .forEach(objectBean -> {
                            Random random = new Random();
                            int length = 10;
                            int x = random.nextInt(length) - length / 2;
                            int y = random.nextInt(length) - length / 2;

                            objectBean.setPos(Const.X, x);
                            objectBean.setPos(Const.Y, y);
                        }
                );

        fieldPublisher.onNext(fieldBean);
    }
}



