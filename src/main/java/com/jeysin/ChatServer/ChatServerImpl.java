package com.jeysin.ChatServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: Jeysin
 * @Date: 2019/3/21 16:22
 * @Desc:
 */

public class ChatServerImpl {

    public static void server(int port) throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer())
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("ChatServer已启动");
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器socket关闭
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("ChatServer已关闭");
        }
    }

    public static void main(String[] args) throws Exception{
        ChatServerImpl.server(10001);
    }
}
