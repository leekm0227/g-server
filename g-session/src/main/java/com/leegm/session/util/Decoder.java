package com.leegm.session.util;

import com.leegm.common.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;


public class Decoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(Decoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        try {
            Message message = Message.getRootAsMessage(ByteBuffer.wrap(bytes));
            out.add(message);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
