package com.kky.netty.netty02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public Channel channel = null;

    public void connect() {
        /*
         * 事件处理的线程池，无论是连接还是读写都由线程池中的线程处理
         * 参数为线程池的大小，无参默认为CPU的核心数*2
         * 客户端一般一个线程就够了
         */
        EventLoopGroup group = new NioEventLoopGroup(1);

        //复制启动类
        Bootstrap bootstrap = new Bootstrap();

        try {
            //设置线程池
            ChannelFuture channelFuture = bootstrap.group(group)
                    //设置连接类型
                    .channel(NioSocketChannel.class)
                    //channel上有事件时传给谁处理
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 8890);

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
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //连接时写出一个Msg
        ctx.writeAndFlush(new Msg(5, 8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Msg m = (Msg) msg;
        System.out.println(m);
    }

}





