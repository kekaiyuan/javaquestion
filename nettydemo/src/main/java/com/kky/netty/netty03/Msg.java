package com.kky.netty.netty03;

public abstract class Msg {
    //客户端收到消息后的处理
    public abstract void clientHandle();
    //服务器收到消息后的处理
    public abstract void serverHandle();
    //把对象转为byte[]以用于消息传输
    public abstract byte[] toBytes();
    //把byte[]转为对象
    public abstract void parse(byte[] bytes);
    //返回消息类型
    public abstract MsgType getMsgType();
}
