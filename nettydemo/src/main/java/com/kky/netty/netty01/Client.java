package com.kky.netty.netty01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    //客户端与服务器之间的通道
    private Channel channel = null;

    public void connect() {
        /*
         * 事件处理的线程池，无论是连接还是读写都由线程池中的线程处理
         * 参数为线程池的大小，无参默认为CPU的核心数*2
         * 客户端一般一个线程就够了
         */
        EventLoopGroup group = new NioEventLoopGroup(1);

        //启动类，用于启动通道
        Bootstrap bootstrap = new Bootstrap();

        try {
            //记录通道返回的结果
            ChannelFuture channelFuture = bootstrap.group(group)
                    //设置连接类型
                    .channel(NioSocketChannel.class)
                    //channel上有事件时传给谁处理
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 9000);

            //设置监听器，监听连接的返回
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("connected");
                        channel = future.channel();
                    } else {
                        System.out.println("not connected");
                    }
                }
            });

            //会永远等待close()方法的调用,实现长时间启动
            try {
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    //发送消息
    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }

    //需要关闭连接时需要给服务器发送什么消息
    public void closeConnect() {
        send("_bye_");
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {

    //通道连接时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ClientFrame.INSTANCE.updateText("server connected");
    }

    //从通道读到消息时触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            ClientFrame.INSTANCE.updateText(new String(bytes));
            System.out.println(new String(bytes));
        } finally {
            //关闭资源
            ReferenceCountUtil.release(msg);
        }
    }
}





