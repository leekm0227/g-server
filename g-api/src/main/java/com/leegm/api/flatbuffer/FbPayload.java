package com.leegm.api.flatbuffer;// automatically generated by the FlatBuffers compiler, do not modify

public final class FbPayload {
  private FbPayload() { }
  public static final byte NONE = 0;
  public static final byte FbChat = 1;
  public static final byte FbAction = 2;
  public static final byte FbField = 3;
  public static final byte FbSignIn = 4;
  public static final byte FbInventory = 5;
  public static final byte FbCharacter = 6;

  public static final String[] names = { "NONE", "FbChat", "FbAction", "FbField", "FbSignIn", "FbInventory", "FbCharacter", };

  public static String name(int e) { return names[e]; }
}

