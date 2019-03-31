package com.jeysin.netty.NIO;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @Author: Jeysin
 * @Date: 2019/3/6 16:40
 * @Desc:
 */

public class NettyNIOServer {
    public void server(Integer port) throws Exception{
        /**
         * 这句如果这样写的话会出问题，客户端只能连接一次，不能重复连接。
         * 原因是ByteBuf会被释放, 引用计数被置为0，然后第二次再访问这个ByteBuf的时候会抛出一个IllegalReferenceCountException异常
         */
        //ByteBuf byteBuf = Unpooled.copiedBuffer("Hi, jeysin, 你好", Charset.forName("UTF-8"));

        final ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi, jeysin, 你好", Charset.forName("UTF-8")));
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.writeAndFlush(byteBuf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
        NettyNIOServer nettyNIOServer = new NettyNIOServer();
        nettyNIOServer.server(10001);
    }
}
