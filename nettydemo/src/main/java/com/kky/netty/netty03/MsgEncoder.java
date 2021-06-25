package com.kky.netty.netty03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {
        //写出Msg的类型
        out.writeInt(msg.getMsgType().ordinal());

        //写出数据长度
        byte[] bytes = msg.toBytes();
        out.writeInt(bytes.length);

        //写出数据
        out.writeBytes(bytes);
    }
}
