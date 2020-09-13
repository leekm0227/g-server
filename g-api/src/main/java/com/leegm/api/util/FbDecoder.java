package com.leegm.api.util;

import com.leegm.api.flatbuffer.FbMessage;
import com.leegm.api.model.RequestBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;


public class FbDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FbDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);

        try {
            FbMessage message = FbMessage.getRootAsFbMessage(ByteBuffer.wrap(bytes));
            out.add(new RequestBean(ctx.channel().id().toString(), message));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
