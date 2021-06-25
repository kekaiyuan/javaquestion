package com.kky.netty.netty02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //两个Int加起来是8字节，没读全之前不处理
        if (in.readableBytes() < 8) {
            return;
        }

        int x = in.readInt();
        int y = in.readInt();
        out.add(new Msg(x, y));
    }
}
