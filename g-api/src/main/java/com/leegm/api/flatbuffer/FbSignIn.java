package com.leegm.api.flatbuffer;// automatically generated by the FlatBuffers compiler, do not modify

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FbSignIn extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static FbSignIn getRootAsFbSignIn(ByteBuffer _bb) { return getRootAsFbSignIn(_bb, new FbSignIn()); }
  public static FbSignIn getRootAsFbSignIn(ByteBuffer _bb, FbSignIn obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public FbSignIn __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String uid() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer uidAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer uidInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String pid() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer pidAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer pidInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createFbSignIn(FlatBufferBuilder builder,
      int uidOffset,
      int pidOffset) {
    builder.startTable(2);
    FbSignIn.addPid(builder, pidOffset);
    FbSignIn.addUid(builder, uidOffset);
    return FbSignIn.endFbSignIn(builder);
  }

  public static void startFbSignIn(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addUid(FlatBufferBuilder builder, int uidOffset) { builder.addOffset(0, uidOffset, 0); }
  public static void addPid(FlatBufferBuilder builder, int pidOffset) { builder.addOffset(1, pidOffset, 0); }
  public static int endFbSignIn(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public FbSignIn get(int j) { return get(new FbSignIn(), j); }
    public FbSignIn get(FbSignIn obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

