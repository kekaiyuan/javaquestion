package com.kky.netty.netty03;

import io.netty.bootstrap.ServerBootstrap;
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

    //????????????????????????????????????????????????????????????????????????????????????
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        //???????????????????????????
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //????????????Socket?????????????????????
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /*
                      ??????????????? channel ????????????
                      ??????????????????
                    */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new ServerChildHandler());
                        }
                    })
                    //????????????
                    .bind(7000)
                    //????????????????????????????????????
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server started");

            //???????????????close()???????????????,?????????????????????
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

    //???????????????????????????????????????????????????
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ServerFrame.INSTANCE.updateServerMsg("A client is connected");
        Server.clients.add(ctx.channel());
    }

    //?????????????????????????????????
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //?????????????????????????????????,??????????????????????????????
        Server.clients.writeAndFlush(msg);

        //????????????????????????????????????
        Msg m = (Msg) msg;
        m.serverHandle();
    }

    //????????????
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //????????????
        cause.printStackTrace();
        //????????????
        Server.clients.remove(ctx.channel());
        ctx.close();
    }
}

