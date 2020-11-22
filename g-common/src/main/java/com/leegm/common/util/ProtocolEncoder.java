package com.leegm.common.util;

import com.leegm.common.protocol.Message;
import com.leegm.common.protocol.Payload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class ProtocolEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        if (msg.getByteBuffer() != null) {
            byte[] array = msg.getByteBuffer().array();
            int size = array.length;
            out.writeInt(size);
            out.writeBytes(array);
        }
    }
}
