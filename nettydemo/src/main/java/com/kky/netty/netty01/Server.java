package com.kky.netty.netty01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * I/O Request
 * via {@link Channel} or
 * {@link ChannelHandlerContext}
 * |
 * +---------------------------------------------------+---------------+
 * |                           ChannelPipeline         |               |
 * |                                                  \|/              |
 * |    +---------------------+            +-----------+----------+    |
 * |    | Inbound Handler  N  |            | Outbound Handler  1  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * |               |                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler N-1 |            | Outbound Handler  2  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  .               |
 * |               .                                   .               |
 * | ChannelHandlerContext.fireIN_EVT() ChannelHandlerContext.OUT_EVT()|
 * |        [ method call]                       [method call]         |
 * |               .                                   .               |
 * |               .                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler  2  |            | Outbound Handler M-1 |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * |               |                                  \|/              |
 * |    +----------+----------+            +-----------+----------+    |
 * |    | Inbound Handler  1  |            | Outbound Handler  M  |    |
 * |    +----------+----------+            +-----------+----------+    |
 * |              /|\                                  |               |
 * +---------------+-----------------------------------+---------------+
 * |                                  \|/
 * +---------------+-----------------------------------+---------------+
 * |               |                                   |               |
 * |       [ Socket.read() ]                    [ Socket.write() ]     |
 * |                                                                   |
 * |  Netty Internal I/O Threads (Transport Implementation)            |
 * +-------------------------------------------------------------------+
 */
public class Server {

    //开启一个客户端通道组，用一个默认的线程处理事件，实现转发
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //只负责客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //负责每个Socket上的事件的处理
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerChildHandler());
                        }
                    })
                    //绑定窗口
                    .bind(9000)
                    //同步阻塞，等待初始化成功
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server started");

            //会永远等待close()方法的调用,实现长时间启动
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter {

    //通道连接时触发，把该通道加入通道组
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ServerFrame.INSTANCE.updateServerMsg("A client is connected");
        Server.clients.add(ctx.channel());
    }

    //从通道读取到数据时触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = null;
        buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), bytes);
        String str = new String(bytes);
        ServerFrame.INSTANCE.updateClientMsg(str);

        if (str.equals("_bye_")) {
            ServerFrame.INSTANCE.updateServerMsg("A client asks to exit");
            Server.clients.remove(ctx.channel());
            ctx.close();
        }

        //该函数会自动释放资源
        //把消息群发给所有客户端
        Server.clients.writeAndFlush(buf);
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        cause.printStackTrace();
        //关闭资源
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}

