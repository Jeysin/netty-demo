package com.jeysin.EchoServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.channels.Channel;

/**
 * @Author: Jeysin
 * @Date: 2019/3/4 11:28
 * @Desc:
 */

public class EchoServerImpl {

    public static void test1() throws Exception{
        int port = 10001;
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(echoServerHandler);
                    }
                });
            ChannelFuture f = serverBootstrap.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    //或者另外一种写法
    public static void test2() throws Exception{
       int port = 10001;
       final EchoServerHandler echoServerHandler = new EchoServerHandler();
       EventLoopGroup group = new NioEventLoopGroup();
       ServerBootstrap bootstrap = new ServerBootstrap();
       bootstrap.group(group)
               .channel(NioServerSocketChannel.class)
               .childHandler(echoServerHandler);
       ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
       future.addListener(new ChannelFutureListener() {
           @Override
           public void operationComplete(ChannelFuture channelFuture) throws Exception {
               if(channelFuture.isSuccess()){
                   System.out.println("Server bound");
               } else {
                   System.out.println("Bound attempt faild");
                   channelFuture.cause().printStackTrace();
               }
           }
       });
    }

   public static void main(String[] args) throws Exception{
        test2();
    }
}
