// automatically generated by the FlatBuffers compiler, do not modify

package com.leegm.common.protocol;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Chat extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static Chat getRootAsChat(ByteBuffer _bb) { return getRootAsChat(_bb, new Chat()); }
  public static Chat getRootAsChat(ByteBuffer _bb, Chat obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Chat __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String topic() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer topicAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer topicInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String userId() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer userIdAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer userIdInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public String name() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer nameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  public String content() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer contentAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public ByteBuffer contentInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 10, 1); }

  public static int createChat(FlatBufferBuilder builder,
      int topicOffset,
      int userIdOffset,
      int nameOffset,
      int contentOffset) {
    builder.startTable(4);
    Chat.addContent(builder, contentOffset);
    Chat.addName(builder, nameOffset);
    Chat.addUserId(builder, userIdOffset);
    Chat.addTopic(builder, topicOffset);
    return Chat.endChat(builder);
  }

  public static void startChat(FlatBufferBuilder builder) { builder.startTable(4); }
  public static void addTopic(FlatBufferBuilder builder, int topicOffset) { builder.addOffset(0, topicOffset, 0); }
  public static void addUserId(FlatBufferBuilder builder, int userIdOffset) { builder.addOffset(1, userIdOffset, 0); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(2, nameOffset, 0); }
  public static void addContent(FlatBufferBuilder builder, int contentOffset) { builder.addOffset(3, contentOffset, 0); }
  public static int endChat(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Chat get(int j) { return get(new Chat(), j); }
    public Chat get(Chat obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

