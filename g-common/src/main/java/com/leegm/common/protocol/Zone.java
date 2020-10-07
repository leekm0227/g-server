// automatically generated by the FlatBuffers compiler, do not modify

package com.leegm.common.protocol;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Zone extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static Zone getRootAsZone(ByteBuffer _bb) { return getRootAsZone(_bb, new Zone()); }
  public static Zone getRootAsZone(ByteBuffer _bb, Zone obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Zone __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public long zoneId() { int o = __offset(4); return o != 0 ? (long)bb.getInt(o + bb_pos) & 0xFFFFFFFFL : 0L; }
  public com.leegm.common.protocol.Object objects(int j) { return objects(new com.leegm.common.protocol.Object(), j); }
  public com.leegm.common.protocol.Object objects(com.leegm.common.protocol.Object obj, int j) { int o = __offset(6); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int objectsLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public com.leegm.common.protocol.Object.Vector objectsVector() { return objectsVector(new com.leegm.common.protocol.Object.Vector()); }
  public com.leegm.common.protocol.Object.Vector objectsVector(com.leegm.common.protocol.Object.Vector obj) { int o = __offset(6); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createZone(FlatBufferBuilder builder,
      long zoneId,
      int objectsOffset) {
    builder.startTable(2);
    Zone.addObjects(builder, objectsOffset);
    Zone.addZoneId(builder, zoneId);
    return Zone.endZone(builder);
  }

  public static void startZone(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addZoneId(FlatBufferBuilder builder, long zoneId) { builder.addInt(0, (int)zoneId, (int)0L); }
  public static void addObjects(FlatBufferBuilder builder, int objectsOffset) { builder.addOffset(1, objectsOffset, 0); }
  public static int createObjectsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startObjectsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endZone(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Zone get(int j) { return get(new Zone(), j); }
    public Zone get(Zone obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}
