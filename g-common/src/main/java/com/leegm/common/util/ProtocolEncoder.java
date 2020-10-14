package com.leegm.common.util;

import com.leegm.common.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        final byte[] array = msg.getByteBuffer().array();
        final int size = array.length;

        out.writeInt(size);
        out.writeBytes(array);
    }
}
