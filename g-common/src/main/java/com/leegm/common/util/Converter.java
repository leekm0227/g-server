package com.leegm.common.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.model.ObjectBean;
import com.leegm.common.model.ZoneBean;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.*;

public final class Converter {

    public static byte[] response(Context context, byte result) {
        if (result > 0) {
            System.out.println("response error : " + Result.name(result));
        }

        FlatBufferBuilder builder = new FlatBufferBuilder();
        int userIdOffset = builder.createString(context != null ? context.userId() : "");
        int sessionIdOffset = builder.createString(context != null ? context.sessionId() : "");
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        builder.finish(Message.createMessage(builder, contextOffset, Method.NONE, result, Payload.NONE, 0));

        return builder.sizedByteArray();
    }

    public static byte[] toZone(ZoneBean zoneBean) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int[] arrObject = new int[zoneBean.getObjects().size()];

        int index = 0;
        for (ObjectBean object : zoneBean.getObjects()) {
            int name = builder.createString(object.getName());
            Object.startObject(builder);
            Object.addName(builder, name);
            Object.addObjectId(builder, object.getObjectId());
            Object.addType(builder, object.getType());
            Object.addState(builder, object.getState());
            Object.addPosition(builder, Vec3.createVec3(builder,
                    object.getPosition(Const.POS_X),
                    object.getPosition(Const.POS_Y),
                    object.getPosition(Const.POS_Z))
            );
            Object.addHp(builder, object.getHp());
            Object.addMp(builder, object.getMp());

            // status
            Object.addPower(builder, object.getPower());
            Object.addDefense(builder, object.getDefense());
            Object.addRange(builder, object.getRange());
            Object.addAttackSpeed(builder, object.getAttackSpeed());
            Object.addMoveSpeed(builder, object.getMoveSpeed());
            Object.addMaxHp(builder, object.getMaxHp());
            Object.addMaxMp(builder, object.getMaxMp());
            int objectOffset = Object.endObject(builder);
            arrObject[index] = objectOffset;
            index++;
        }

        int objectsVector = Zone.createObjectsVector(builder, arrObject);
        int zoneOffset = Zone.createZone(builder, zoneBean.getZoneId(), objectsVector);
        int messageOffset = Message.createMessage(builder, 0, Method.NONE, Result.SUCCESS, Payload.Zone, zoneOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

    public static byte[] toChat(String topic, String userId, String name, String content) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int topicOffset = builder.createString(topic);
        int userIdOffset = builder.createString(userId);
        int nameOffset = builder.createString(name);
        int contentOffset = builder.createString(content);
        int chatOffset = Chat.createChat(builder, topicOffset, userIdOffset, nameOffset, contentOffset);
        int messageOffset = Message.createMessage(builder, 0, Method.NONE, Result.SUCCESS, Payload.Chat, chatOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }
}
