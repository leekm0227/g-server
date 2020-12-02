package com.leegm.session.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SessionDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(SessionDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        logger.info("Decoding... {} byte(s)", in.readableBytes());

        while (true) {
            if (in.readableBytes() < 8) {
                logger.info("Decoding... readableBytes1={}", in.readableBytes());
                return;
            }

            in.markReaderIndex();

            final int size = in.readInt();
            final int hash = in.readInt();
            final int hash1 = in.readInt();
            final int hash2 = in.readInt();
            final int hash3 = in.readInt();
            final int hash4 = in.readInt();
            final int hash5 = in.readInt();
            final int hash6 = in.readInt();
            final int hash7 = in.readInt();

            logger.info("Decoding... size={}, hash={}, {}, {}, {}, {}, {}, {}, {}", size, hash, hash1, hash2, hash3, hash4, hash5, hash6, hash7);

            if (in.readableBytes() < size) {
                logger.info("Decoding... readableBytes2={}", in.readableBytes());
                in.resetReaderIndex();
                return;
            }

//            try {
//                out.add(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
//            } catch (Exception ignored) {
//            }
        }



//        byte[] bytes = new byte[in.readableBytes()];
//        in.readBytes(bytes);
//
//        System.out.println("bytes : "+ Arrays.toString(bytes));
//
//        try {
//            out.add(Message.getRootAsMessage(ByteBuffer.wrap(bytes)));
//        } catch (Exception ignored) {
//        }
    }
}