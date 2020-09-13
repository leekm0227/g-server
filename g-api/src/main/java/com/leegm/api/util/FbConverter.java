package com.leegm.api.util;

import com.leegm.api.domain.Account;
import com.leegm.api.domain.Character;
import com.leegm.api.flatbuffer.*;
import com.leegm.api.model.FieldBean;
import com.leegm.api.model.ObjectBean;
import com.google.flatbuffers.FlatBufferBuilder;

import java.nio.ByteBuffer;
import java.util.Optional;

public final class FbConverter {

    public static byte[] toEmpty() {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        builder.finish(FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.NONE, 0));

        return builder.sizedByteArray();
    }

    public static byte[] toField(FieldBean fieldBean) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int[] arrObject = new int[fieldBean.getObjects().size()];

        int index = 0;
        for (ObjectBean object : fieldBean.getObjects()) {
            int oid = builder.createString(object.getOid());
            int name = builder.createString(object.getName());
            FbObject.startFbObject(builder);
            FbObject.addOid(builder, oid);
            FbObject.addName(builder, name);
            FbObject.addPos(builder, FbVec3.createFbVec3(builder, object.getPos(Const.X), object.getPos(Const.Y), object.getPos(Const.Z)));
            FbObject.addState(builder, object.getState());
            FbObject.addType(builder, object.getType());
            int objectOffset = FbObject.endFbObject(builder);
            arrObject[index] = objectOffset;
            index++;
        }

        int objectsVector = FbField.createObjectsVector(builder, arrObject);
        int fieldOffset = FbField.createFbField(builder, objectsVector);
        int messageOffset = FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.FbField, fieldOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

    public static byte[] toSignIn(Account account) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int pid = builder.createString(Optional.ofNullable(account.getPid()).orElse(""));
        int uid = builder.createString(Optional.ofNullable(account.getId()).orElse(""));
        int signInOffset = FbSignIn.createFbSignIn(builder, uid, pid);
        int messageOffset = FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.FbSignIn, signInOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

    public static byte[] toCharacter(Account account) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int[] arrChar = new int[account.getCharacters().size()];

        int index = 0;
        for (Character character : account.getCharacters()) {
            int oid = builder.createString(character.getId());
            int name = builder.createString(character.getName());
            FbObject.startFbObject(builder);
            FbObject.addOid(builder, oid);
            FbObject.addName(builder, name);
            FbObject.addPos(builder, FbVec3.createFbVec3(builder, character.getPos(Const.X), character.getPos(Const.Y), character.getPos(Const.Z)));
            FbObject.addState(builder, FbState.I);
            FbObject.addType(builder, FbType.P);
            int objectOffset = FbObject.endFbObject(builder);
            arrChar[index] = objectOffset;
            index++;
        }

        int uid = builder.createString(Optional.ofNullable(account.getId()).orElse(""));
        int objectsVector = FbCharacter.createObjectsVector(builder, arrChar);
        int signInOffset = FbCharacter.createFbCharacter(builder, uid, objectsVector);
        int messageOffset = FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.FbCharacter, signInOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

    public static byte[] toChat(String cid, String oid, String content) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int cidOffset = builder.createString(cid);
        int oidOffset = builder.createString(oid);
        int contentOffset = builder.createString(content);
        int chatOffset = FbChat.createFbChat(builder, cidOffset, oidOffset, contentOffset);
        int messageOffset = FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.FbChat, chatOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }

    public static byte[] toAction(Character character, byte state) {
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int oid = builder.createString(Optional.ofNullable(character.getId()).orElse(""));
        int name = builder.createString(Optional.ofNullable(character.getName()).orElse(""));
        FbObject.startFbObject(builder);
        FbObject.addOid(builder, oid);
        FbObject.addName(builder, name);
        FbObject.addPos(builder, FbVec3.createFbVec3(builder, character.getPos(Const.X), character.getPos(Const.Y), character.getPos(Const.Z)));
        FbObject.addState(builder, state);
        FbObject.addType(builder, FbType.P);
        int objectOffset = FbObject.endFbObject(builder);
        int actionOffset = FbAction.createFbAction(builder, objectOffset);
        int messageOffset = FbMessage.createFbMessage(builder, FbMethod.N, FbResult.S, FbPayload.FbAction, actionOffset);
        builder.finish(messageOffset);

        return builder.sizedByteArray();
    }
}
