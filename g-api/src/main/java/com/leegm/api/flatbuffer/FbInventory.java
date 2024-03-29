package com.leegm.api.flatbuffer;// automatically generated by the FlatBuffers compiler, do not modify

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FbInventory extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_1_12_0(); }
  public static FbInventory getRootAsFbInventory(ByteBuffer _bb) { return getRootAsFbInventory(_bb, new FbInventory()); }
  public static FbInventory getRootAsFbInventory(ByteBuffer _bb, FbInventory obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public FbInventory __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public FbItem items(int j) { return items(new FbItem(), j); }
  public FbItem items(FbItem obj, int j) { int o = __offset(4); return o != 0 ? obj.__assign(__vector(o) + j * 16, bb) : null; }
  public int itemsLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public FbItem.Vector itemsVector() { return itemsVector(new FbItem.Vector()); }
  public FbItem.Vector itemsVector(FbItem.Vector obj) { int o = __offset(4); return o != 0 ? obj.__assign(__vector(o), 16, bb) : null; }

  public static int createFbInventory(FlatBufferBuilder builder,
      int itemsOffset) {
    builder.startTable(1);
    FbInventory.addItems(builder, itemsOffset);
    return FbInventory.endFbInventory(builder);
  }

  public static void startFbInventory(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addItems(FlatBufferBuilder builder, int itemsOffset) { builder.addOffset(0, itemsOffset, 0); }
  public static void startItemsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(16, numElems, 8); }
  public static int endFbInventory(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public FbInventory get(int j) { return get(new FbInventory(), j); }
    public FbInventory get(FbInventory obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

