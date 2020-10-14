package com.leegm.common.util;

import com.leegm.common.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

public class ProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (true) {
            if (in.readableBytes() < 8) {
                return;
            }

            in.markReaderIndex();
            final int size = in.readInt();

            if (in.readableBytes() < size) {
                in.resetReaderIndex();
                return;
            }

            try {
                final byte[] array = new byte[size];
                in.readBytes(array);
                out.add(Message.getRootAsMessage(ByteBuffer.wrap(array)));
            } catch (Exception e) {
                throw new RuntimeException("protocol decode err");
            }
        }
    }
}
