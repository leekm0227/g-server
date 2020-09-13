package com.leegm.api.handler;

import com.leegm.api.flatbuffer.FbAction;
import com.leegm.api.flatbuffer.FbPayload;
import com.leegm.api.flatbuffer.FbState;
import com.leegm.api.model.FieldBean;
import com.leegm.api.publisher.FieldPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActionHandler extends AbstractHandler<FbAction> {

    @Autowired
    FieldPublisher fieldPublisher;

    @PostConstruct
    public void init() {
        cls = FbAction.class;
        demoHandlerFactory.register(FbPayload.FbAction, this);
    }

    @Override
    public byte[] handle(String sid, FbAction action, byte method) {
        switch (action.object().state()) {
            case FbState.A:

                break;
            case FbState.D:
                fieldPublisher.onNext(new FieldBean());
                break;
            case FbState.I:
            case FbState.M:
                fieldPublisher.update(action.object());
                break;
            case FbState.S:
                fieldPublisher.spawn(action.object());
                break;
        }

        return empty();
    }
}
