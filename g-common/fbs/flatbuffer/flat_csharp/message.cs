// <auto-generated>
//  automatically generated by the FlatBuffers compiler, do not modify
// </auto-generated>

namespace com.leegm.common.protocol
{

using global::System;
using global::System.Collections.Generic;
using global::FlatBuffers;

public enum Result : sbyte
{
  SUCCESS = 0,
  ERROR_INVALID_MESSAGE = 1,
  ERROR_HANDLER_NOT_FOUND = 2,
  ERROR_CLS_NOT_FOUND = 3,
};

public enum Method : sbyte
{
  NONE = 0,
  CREATE = 1,
  READ = 2,
  UPDATE = 3,
  DELETE = 4,
};

public enum Type : sbyte
{
  PLAYER = 0,
  NPC = 1,
  OBJECT = 2,
};

public enum State : sbyte
{
  IDLE = 0,
  MOVE = 1,
  ATTACK = 2,
  DEAD = 3,
};

public enum Event : sbyte
{
  UP = 0,
  DOWN = 1,
  LEFT = 2,
  RIGHT = 3,
  ATTACK = 4,
};

public enum Payload : byte
{
  NONE = 0,
  Chat = 1,
  Zone = 2,
  Inventory = 3,
  Action = 4,
};

public struct Item : IFlatbufferObject
{
  private Struct __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public void __init(int _i, ByteBuffer _bb) { __p = new Struct(_i, _bb); }
  public Item __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public uint TemplateId { get { return __p.bb.GetUint(__p.bb_pos + 0); } }
  public uint Amount { get { return __p.bb.GetUint(__p.bb_pos + 4); } }

  public static Offset<com.leegm.common.protocol.Item> CreateItem(FlatBufferBuilder builder, uint TemplateId, uint Amount) {
    builder.Prep(4, 8);
    builder.PutUint(Amount);
    builder.PutUint(TemplateId);
    return new Offset<com.leegm.common.protocol.Item>(builder.Offset);
  }
};

public struct Vec3 : IFlatbufferObject
{
  private Struct __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public void __init(int _i, ByteBuffer _bb) { __p = new Struct(_i, _bb); }
  public Vec3 __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public float X { get { return __p.bb.GetFloat(__p.bb_pos + 0); } }
  public float Y { get { return __p.bb.GetFloat(__p.bb_pos + 4); } }
  public float Z { get { return __p.bb.GetFloat(__p.bb_pos + 8); } }

  public static Offset<com.leegm.common.protocol.Vec3> CreateVec3(FlatBufferBuilder builder, float X, float Y, float Z) {
    builder.Prep(4, 12);
    builder.PutFloat(Z);
    builder.PutFloat(Y);
    builder.PutFloat(X);
    return new Offset<com.leegm.common.protocol.Vec3>(builder.Offset);
  }
};

public struct Object : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Object GetRootAsObject(ByteBuffer _bb) { return GetRootAsObject(_bb, new Object()); }
  public static Object GetRootAsObject(ByteBuffer _bb, Object obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Object __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public string Name { get { int o = __p.__offset(4); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetNameBytes() { return __p.__vector_as_span<byte>(4, 1); }
#else
  public ArraySegment<byte>? GetNameBytes() { return __p.__vector_as_arraysegment(4); }
#endif
  public byte[] GetNameArray() { return __p.__vector_as_array<byte>(4); }
  public uint ObjectId { get { int o = __p.__offset(6); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public com.leegm.common.protocol.Type Type { get { int o = __p.__offset(8); return o != 0 ? (com.leegm.common.protocol.Type)__p.bb.GetSbyte(o + __p.bb_pos) : com.leegm.common.protocol.Type.PLAYER; } }
  public com.leegm.common.protocol.State State { get { int o = __p.__offset(10); return o != 0 ? (com.leegm.common.protocol.State)__p.bb.GetSbyte(o + __p.bb_pos) : com.leegm.common.protocol.State.IDLE; } }
  public com.leegm.common.protocol.Vec3? Position { get { int o = __p.__offset(12); return o != 0 ? (com.leegm.common.protocol.Vec3?)(new com.leegm.common.protocol.Vec3()).__assign(o + __p.bb_pos, __p.bb) : null; } }
  public com.leegm.common.protocol.Vec3? Direction { get { int o = __p.__offset(14); return o != 0 ? (com.leegm.common.protocol.Vec3?)(new com.leegm.common.protocol.Vec3()).__assign(o + __p.bb_pos, __p.bb) : null; } }
  public uint Hp { get { int o = __p.__offset(16); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public uint Mp { get { int o = __p.__offset(18); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public float Power { get { int o = __p.__offset(20); return o != 0 ? __p.bb.GetFloat(o + __p.bb_pos) : (float)0.0f; } }
  public float Defense { get { int o = __p.__offset(22); return o != 0 ? __p.bb.GetFloat(o + __p.bb_pos) : (float)0.0f; } }
  public uint Range { get { int o = __p.__offset(24); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public float AttackSpeed { get { int o = __p.__offset(26); return o != 0 ? __p.bb.GetFloat(o + __p.bb_pos) : (float)0.0f; } }
  public float MoveSpeed { get { int o = __p.__offset(28); return o != 0 ? __p.bb.GetFloat(o + __p.bb_pos) : (float)0.0f; } }
  public uint MaxHp { get { int o = __p.__offset(30); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public uint MaxMp { get { int o = __p.__offset(32); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }

  public static void StartObject(FlatBufferBuilder builder) { builder.StartTable(15); }
  public static void AddName(FlatBufferBuilder builder, StringOffset nameOffset) { builder.AddOffset(0, nameOffset.Value, 0); }
  public static void AddObjectId(FlatBufferBuilder builder, uint objectId) { builder.AddUint(1, objectId, 0); }
  public static void AddType(FlatBufferBuilder builder, com.leegm.common.protocol.Type type) { builder.AddSbyte(2, (sbyte)type, 0); }
  public static void AddState(FlatBufferBuilder builder, com.leegm.common.protocol.State state) { builder.AddSbyte(3, (sbyte)state, 0); }
  public static void AddPosition(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Vec3> positionOffset) { builder.AddStruct(4, positionOffset.Value, 0); }
  public static void AddDirection(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Vec3> directionOffset) { builder.AddStruct(5, directionOffset.Value, 0); }
  public static void AddHp(FlatBufferBuilder builder, uint hp) { builder.AddUint(6, hp, 0); }
  public static void AddMp(FlatBufferBuilder builder, uint mp) { builder.AddUint(7, mp, 0); }
  public static void AddPower(FlatBufferBuilder builder, float power) { builder.AddFloat(8, power, 0.0f); }
  public static void AddDefense(FlatBufferBuilder builder, float defense) { builder.AddFloat(9, defense, 0.0f); }
  public static void AddRange(FlatBufferBuilder builder, uint range) { builder.AddUint(10, range, 0); }
  public static void AddAttackSpeed(FlatBufferBuilder builder, float attackSpeed) { builder.AddFloat(11, attackSpeed, 0.0f); }
  public static void AddMoveSpeed(FlatBufferBuilder builder, float moveSpeed) { builder.AddFloat(12, moveSpeed, 0.0f); }
  public static void AddMaxHp(FlatBufferBuilder builder, uint maxHp) { builder.AddUint(13, maxHp, 0); }
  public static void AddMaxMp(FlatBufferBuilder builder, uint maxMp) { builder.AddUint(14, maxMp, 0); }
  public static Offset<com.leegm.common.protocol.Object> EndObject(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Object>(o);
  }
};

public struct Context : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Context GetRootAsContext(ByteBuffer _bb) { return GetRootAsContext(_bb, new Context()); }
  public static Context GetRootAsContext(ByteBuffer _bb, Context obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Context __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public string UserId { get { int o = __p.__offset(4); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetUserIdBytes() { return __p.__vector_as_span<byte>(4, 1); }
#else
  public ArraySegment<byte>? GetUserIdBytes() { return __p.__vector_as_arraysegment(4); }
#endif
  public byte[] GetUserIdArray() { return __p.__vector_as_array<byte>(4); }
  public string SessionId { get { int o = __p.__offset(6); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetSessionIdBytes() { return __p.__vector_as_span<byte>(6, 1); }
#else
  public ArraySegment<byte>? GetSessionIdBytes() { return __p.__vector_as_arraysegment(6); }
#endif
  public byte[] GetSessionIdArray() { return __p.__vector_as_array<byte>(6); }

  public static Offset<com.leegm.common.protocol.Context> CreateContext(FlatBufferBuilder builder,
      StringOffset userIdOffset = default(StringOffset),
      StringOffset sessionIdOffset = default(StringOffset)) {
    builder.StartTable(2);
    Context.AddSessionId(builder, sessionIdOffset);
    Context.AddUserId(builder, userIdOffset);
    return Context.EndContext(builder);
  }

  public static void StartContext(FlatBufferBuilder builder) { builder.StartTable(2); }
  public static void AddUserId(FlatBufferBuilder builder, StringOffset userIdOffset) { builder.AddOffset(0, userIdOffset.Value, 0); }
  public static void AddSessionId(FlatBufferBuilder builder, StringOffset sessionIdOffset) { builder.AddOffset(1, sessionIdOffset.Value, 0); }
  public static Offset<com.leegm.common.protocol.Context> EndContext(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Context>(o);
  }
};

public struct Zone : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Zone GetRootAsZone(ByteBuffer _bb) { return GetRootAsZone(_bb, new Zone()); }
  public static Zone GetRootAsZone(ByteBuffer _bb, Zone obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Zone __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public uint ZoneId { get { int o = __p.__offset(4); return o != 0 ? __p.bb.GetUint(o + __p.bb_pos) : (uint)0; } }
  public com.leegm.common.protocol.Object? Objects(int j) { int o = __p.__offset(6); return o != 0 ? (com.leegm.common.protocol.Object?)(new com.leegm.common.protocol.Object()).__assign(__p.__indirect(__p.__vector(o) + j * 4), __p.bb) : null; }
  public int ObjectsLength { get { int o = __p.__offset(6); return o != 0 ? __p.__vector_len(o) : 0; } }

  public static Offset<com.leegm.common.protocol.Zone> CreateZone(FlatBufferBuilder builder,
      uint zoneId = 0,
      VectorOffset objectsOffset = default(VectorOffset)) {
    builder.StartTable(2);
    Zone.AddObjects(builder, objectsOffset);
    Zone.AddZoneId(builder, zoneId);
    return Zone.EndZone(builder);
  }

  public static void StartZone(FlatBufferBuilder builder) { builder.StartTable(2); }
  public static void AddZoneId(FlatBufferBuilder builder, uint zoneId) { builder.AddUint(0, zoneId, 0); }
  public static void AddObjects(FlatBufferBuilder builder, VectorOffset objectsOffset) { builder.AddOffset(1, objectsOffset.Value, 0); }
  public static VectorOffset CreateObjectsVector(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Object>[] data) { builder.StartVector(4, data.Length, 4); for (int i = data.Length - 1; i >= 0; i--) builder.AddOffset(data[i].Value); return builder.EndVector(); }
  public static VectorOffset CreateObjectsVectorBlock(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Object>[] data) { builder.StartVector(4, data.Length, 4); builder.Add(data); return builder.EndVector(); }
  public static void StartObjectsVector(FlatBufferBuilder builder, int numElems) { builder.StartVector(4, numElems, 4); }
  public static Offset<com.leegm.common.protocol.Zone> EndZone(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Zone>(o);
  }
};

public struct Chat : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Chat GetRootAsChat(ByteBuffer _bb) { return GetRootAsChat(_bb, new Chat()); }
  public static Chat GetRootAsChat(ByteBuffer _bb, Chat obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Chat __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public string Topic { get { int o = __p.__offset(4); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetTopicBytes() { return __p.__vector_as_span<byte>(4, 1); }
#else
  public ArraySegment<byte>? GetTopicBytes() { return __p.__vector_as_arraysegment(4); }
#endif
  public byte[] GetTopicArray() { return __p.__vector_as_array<byte>(4); }
  public string UserId { get { int o = __p.__offset(6); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetUserIdBytes() { return __p.__vector_as_span<byte>(6, 1); }
#else
  public ArraySegment<byte>? GetUserIdBytes() { return __p.__vector_as_arraysegment(6); }
#endif
  public byte[] GetUserIdArray() { return __p.__vector_as_array<byte>(6); }
  public string Name { get { int o = __p.__offset(8); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetNameBytes() { return __p.__vector_as_span<byte>(8, 1); }
#else
  public ArraySegment<byte>? GetNameBytes() { return __p.__vector_as_arraysegment(8); }
#endif
  public byte[] GetNameArray() { return __p.__vector_as_array<byte>(8); }
  public string Content { get { int o = __p.__offset(10); return o != 0 ? __p.__string(o + __p.bb_pos) : null; } }
#if ENABLE_SPAN_T
  public Span<byte> GetContentBytes() { return __p.__vector_as_span<byte>(10, 1); }
#else
  public ArraySegment<byte>? GetContentBytes() { return __p.__vector_as_arraysegment(10); }
#endif
  public byte[] GetContentArray() { return __p.__vector_as_array<byte>(10); }

  public static Offset<com.leegm.common.protocol.Chat> CreateChat(FlatBufferBuilder builder,
      StringOffset topicOffset = default(StringOffset),
      StringOffset userIdOffset = default(StringOffset),
      StringOffset nameOffset = default(StringOffset),
      StringOffset contentOffset = default(StringOffset)) {
    builder.StartTable(4);
    Chat.AddContent(builder, contentOffset);
    Chat.AddName(builder, nameOffset);
    Chat.AddUserId(builder, userIdOffset);
    Chat.AddTopic(builder, topicOffset);
    return Chat.EndChat(builder);
  }

  public static void StartChat(FlatBufferBuilder builder) { builder.StartTable(4); }
  public static void AddTopic(FlatBufferBuilder builder, StringOffset topicOffset) { builder.AddOffset(0, topicOffset.Value, 0); }
  public static void AddUserId(FlatBufferBuilder builder, StringOffset userIdOffset) { builder.AddOffset(1, userIdOffset.Value, 0); }
  public static void AddName(FlatBufferBuilder builder, StringOffset nameOffset) { builder.AddOffset(2, nameOffset.Value, 0); }
  public static void AddContent(FlatBufferBuilder builder, StringOffset contentOffset) { builder.AddOffset(3, contentOffset.Value, 0); }
  public static Offset<com.leegm.common.protocol.Chat> EndChat(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Chat>(o);
  }
};

public struct Inventory : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Inventory GetRootAsInventory(ByteBuffer _bb) { return GetRootAsInventory(_bb, new Inventory()); }
  public static Inventory GetRootAsInventory(ByteBuffer _bb, Inventory obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Inventory __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public com.leegm.common.protocol.Item? Items(int j) { int o = __p.__offset(4); return o != 0 ? (com.leegm.common.protocol.Item?)(new com.leegm.common.protocol.Item()).__assign(__p.__vector(o) + j * 8, __p.bb) : null; }
  public int ItemsLength { get { int o = __p.__offset(4); return o != 0 ? __p.__vector_len(o) : 0; } }

  public static Offset<com.leegm.common.protocol.Inventory> CreateInventory(FlatBufferBuilder builder,
      VectorOffset itemsOffset = default(VectorOffset)) {
    builder.StartTable(1);
    Inventory.AddItems(builder, itemsOffset);
    return Inventory.EndInventory(builder);
  }

  public static void StartInventory(FlatBufferBuilder builder) { builder.StartTable(1); }
  public static void AddItems(FlatBufferBuilder builder, VectorOffset itemsOffset) { builder.AddOffset(0, itemsOffset.Value, 0); }
  public static void StartItemsVector(FlatBufferBuilder builder, int numElems) { builder.StartVector(8, numElems, 4); }
  public static Offset<com.leegm.common.protocol.Inventory> EndInventory(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Inventory>(o);
  }
};

public struct Action : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Action GetRootAsAction(ByteBuffer _bb) { return GetRootAsAction(_bb, new Action()); }
  public static Action GetRootAsAction(ByteBuffer _bb, Action obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Action __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public com.leegm.common.protocol.Object? Object { get { int o = __p.__offset(4); return o != 0 ? (com.leegm.common.protocol.Object?)(new com.leegm.common.protocol.Object()).__assign(__p.__indirect(o + __p.bb_pos), __p.bb) : null; } }

  public static Offset<com.leegm.common.protocol.Action> CreateAction(FlatBufferBuilder builder,
      Offset<com.leegm.common.protocol.Object> objectOffset = default(Offset<com.leegm.common.protocol.Object>)) {
    builder.StartTable(1);
    Action.AddObject(builder, objectOffset);
    return Action.EndAction(builder);
  }

  public static void StartAction(FlatBufferBuilder builder) { builder.StartTable(1); }
  public static void AddObject(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Object> objectOffset) { builder.AddOffset(0, objectOffset.Value, 0); }
  public static Offset<com.leegm.common.protocol.Action> EndAction(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Action>(o);
  }
};

public struct Message : IFlatbufferObject
{
  private Table __p;
  public ByteBuffer ByteBuffer { get { return __p.bb; } }
  public static void ValidateVersion() { FlatBufferConstants.FLATBUFFERS_1_12_0(); }
  public static Message GetRootAsMessage(ByteBuffer _bb) { return GetRootAsMessage(_bb, new Message()); }
  public static Message GetRootAsMessage(ByteBuffer _bb, Message obj) { return (obj.__assign(_bb.GetInt(_bb.Position) + _bb.Position, _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __p = new Table(_i, _bb); }
  public Message __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public com.leegm.common.protocol.Context? Context { get { int o = __p.__offset(4); return o != 0 ? (com.leegm.common.protocol.Context?)(new com.leegm.common.protocol.Context()).__assign(__p.__indirect(o + __p.bb_pos), __p.bb) : null; } }
  public com.leegm.common.protocol.Method Method { get { int o = __p.__offset(6); return o != 0 ? (com.leegm.common.protocol.Method)__p.bb.GetSbyte(o + __p.bb_pos) : com.leegm.common.protocol.Method.NONE; } }
  public com.leegm.common.protocol.Result Result { get { int o = __p.__offset(8); return o != 0 ? (com.leegm.common.protocol.Result)__p.bb.GetSbyte(o + __p.bb_pos) : com.leegm.common.protocol.Result.SUCCESS; } }
  public com.leegm.common.protocol.Payload PayloadType { get { int o = __p.__offset(10); return o != 0 ? (com.leegm.common.protocol.Payload)__p.bb.Get(o + __p.bb_pos) : com.leegm.common.protocol.Payload.NONE; } }
  public TTable? Payload<TTable>() where TTable : struct, IFlatbufferObject { int o = __p.__offset(12); return o != 0 ? (TTable?)__p.__union<TTable>(o + __p.bb_pos) : null; }

  public static Offset<com.leegm.common.protocol.Message> CreateMessage(FlatBufferBuilder builder,
      Offset<com.leegm.common.protocol.Context> contextOffset = default(Offset<com.leegm.common.protocol.Context>),
      com.leegm.common.protocol.Method method = com.leegm.common.protocol.Method.NONE,
      com.leegm.common.protocol.Result result = com.leegm.common.protocol.Result.SUCCESS,
      com.leegm.common.protocol.Payload payload_type = com.leegm.common.protocol.Payload.NONE,
      int payloadOffset = 0) {
    builder.StartTable(5);
    Message.AddPayload(builder, payloadOffset);
    Message.AddContext(builder, contextOffset);
    Message.AddPayloadType(builder, payload_type);
    Message.AddResult(builder, result);
    Message.AddMethod(builder, method);
    return Message.EndMessage(builder);
  }

  public static void StartMessage(FlatBufferBuilder builder) { builder.StartTable(5); }
  public static void AddContext(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Context> contextOffset) { builder.AddOffset(0, contextOffset.Value, 0); }
  public static void AddMethod(FlatBufferBuilder builder, com.leegm.common.protocol.Method method) { builder.AddSbyte(1, (sbyte)method, 0); }
  public static void AddResult(FlatBufferBuilder builder, com.leegm.common.protocol.Result result) { builder.AddSbyte(2, (sbyte)result, 0); }
  public static void AddPayloadType(FlatBufferBuilder builder, com.leegm.common.protocol.Payload payloadType) { builder.AddByte(3, (byte)payloadType, 0); }
  public static void AddPayload(FlatBufferBuilder builder, int payloadOffset) { builder.AddOffset(4, payloadOffset, 0); }
  public static Offset<com.leegm.common.protocol.Message> EndMessage(FlatBufferBuilder builder) {
    int o = builder.EndTable();
    return new Offset<com.leegm.common.protocol.Message>(o);
  }
  public static void FinishMessageBuffer(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Message> offset) { builder.Finish(offset.Value); }
  public static void FinishSizePrefixedMessageBuffer(FlatBufferBuilder builder, Offset<com.leegm.common.protocol.Message> offset) { builder.FinishSizePrefixed(offset.Value); }
};


}
