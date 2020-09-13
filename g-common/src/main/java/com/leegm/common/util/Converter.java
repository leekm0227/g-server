package com.leegm.common.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.leegm.common.model.FieldBean;
import com.leegm.common.model.ObjectBean;
import com.leegm.common.protocol.Object;
import com.leegm.common.protocol.*;

public final class Converter {

    public static byte[] toEmpty() {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        builder.finish(Message.createMessage(builder, 0, MethodCode.NONE, ResultCode.SUCCESS, Payload.NONE, 0));

        return builder.sizedByteArray();
    }

    public static byte[] toField(FieldBean fieldBean) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int[] arrObject = new int[fieldBean.getObjects().size()];

        int index = 0;
        for (ObjectBean object : fieldBean.getObjects()) {
            int oid = builder.createString(object.getOid());
            int name = builder.createString(object.getName());
            Object.startObject(builder);
            Object.addObjectId(builder, oid);
            Object.addName(builder, name);
            Object.addPosition(builder, Vec3.createVec3(builder, object.getPos(Const.POS_X), object.getPos(Const.POS_Y), object.getPos(Const.POS_Z)));
            Object.addState(builder, object.getState());
            Object.addType(builder, object.getType());
            int objectOffset = Object.endObject(builder);
            arrObject[index] = objectOffset;
            index++;
        }

        int objectsVector = Field.createObjectsVector(builder, arrObject);
        int fieldOffset = Field.createField(builder, objectsVector);
        int userIdOffset = builder.createString("");
        int sessionIdOffset = builder.createString("");
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        int messageOffset = Message.createMessage(builder, contextOffset, MethodCode.NONE, ResultCode.SUCCESS, Payload.Field, fieldOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

//    public static byte[] toSignIn(Account account) {
//        FlatBufferBuilder builder = new FlatBufferBuilder();
//        int pid = builder.createString(Optional.ofNullable(account.getPid()).orElse(""));
//        int uid = builder.createString(Optional.ofNullable(account.getId()).orElse(""));
//        int signInOffset = SignIn.createSignIn(builder, uid, pid);
//        int messageOffset = Message.createMessage(builder, Method.N, Result.S, Payload.SignIn, signInOffset);
//        builder.finish(messageOffset);
//
//        return builder.sizedByteArray();
//    }
//
//    public static byte[] toCharacter(Account account) {
//        FlatBufferBuilder builder = new FlatBufferBuilder();
//        int[] arrChar = new int[account.getCharacters().size()];
//
//        int index = 0;
//        for (Character character : account.getCharacters()) {
//            int oid = builder.createString(character.getId());
//            int name = builder.createString(character.getName());
//            Object.startObject(builder);
//            Object.addOid(builder, oid);
//            Object.addName(builder, name);
//            Object.addPos(builder, Vec3.createVec3(builder, character.getPos(Const.POS_X), character.getPos(Const.POS_Y), character.getPos(Const.POS_Z)));
//            Object.addState(builder, State.I);
//            Object.addType(builder, Type.P);
//            int objectOffset = Object.endObject(builder);
//            arrChar[index] = objectOffset;
//            index++;
//        }
//
//        int uid = builder.createString(Optional.ofNullable(account.getId()).orElse(""));
//        int objectsVector = Character.createObjectsVector(builder, arrChar);
//        int signInOffset = Character.createCharacter(builder, uid, objectsVector);
//        int messageOffset = Message.createMessage(builder, Method.N, Result.S, Payload.Character, signInOffset);
//        builder.finish(messageOffset);
//
//        return builder.sizedByteArray();
//    }

    public static byte[] toChat(String cid, String oid, String content) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int cidOffset = builder.createString(cid);
        int oidOffset = builder.createString(oid);
        int contentOffset = builder.createString(content);
        int chatOffset = Chat.createChat(builder, cidOffset, oidOffset, contentOffset);
        int userIdOffset = builder.createString("");
        int sessionIdOffset = builder.createString("");
        int contextOffset = Context.createContext(builder, userIdOffset, sessionIdOffset);
        int messageOffset = Message.createMessage(builder, contextOffset, MethodCode.NONE, ResultCode.SUCCESS, Payload.Chat, chatOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

//    public static byte[] toAction(Character character, byte state) {
//        FlatBufferBuilder builder = new FlatBufferBuilder();
//        int oid = builder.createString(Optional.ofNullable(character.getId()).orElse(""));
//        int name = builder.createString(Optional.ofNullable(character.getName()).orElse(""));
//        Object.startObject(builder);
//        Object.addOid(builder, oid);
//        Object.addName(builder, name);
//        Object.addPos(builder, Vec3.createVec3(builder, character.getPos(Const.POS_X), character.getPos(Const.POS_Y), character.getPos(Const.POS_Z)));
//        Object.addState(builder, state);
//        Object.addType(builder, Type.P);
//        int objectOffset = Object.endObject(builder);
//        int actionOffset = Action.createAction(builder, objectOffset);
//        int messageOffset = Message.createMessage(builder, Method.N, Result.S, Payload.Action, actionOffset);
//        builder.finish(messageOffset);
//
//        return builder.sizedByteArray();
//    }
}
