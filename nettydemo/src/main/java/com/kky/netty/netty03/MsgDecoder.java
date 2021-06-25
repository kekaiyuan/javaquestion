package com.kky.netty.netty03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        /*
        先读取数据类型和数据长度，其长度为8字节（两个int类型）
        如果长度不满8字节，退出，等待下一个数据包的到来
         */
        System.out.println(in.readerIndex());
        if (in.readableBytes() < 100) {
            return;
        }

        //标记读取位置
        in.markReaderIndex();
        System.out.println(in.readerIndex());

        //读取数据类型
        MsgType msgType = MsgType.values()[in.readInt()];
        //读取数据长度
        int length = in.readInt();

        //如果剩下可读取字节小于数据长度，重置读取位置，退出
        if(in.readableBytes()<length){
            //回退到in.markReaderIndex()标记的位置
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        switch (msgType){
            case Msg01:
                //把bytes转为Msg01对象
                Msg01 msg01 = new Msg01();
                msg01.parse(bytes);

                //把对象加入输出队列
                out.add(msg01);
                break;
            case Msg02:
                Msg02 msg02 = new Msg02();
                msg02.parse(bytes);
                out.add(msg02);
                break;
        }

    }
}
