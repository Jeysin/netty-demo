package com.jeysin.ChatServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: Jeysin
 * @Date: 2019/3/21 16:42
 * @Desc:
 */

public class ChatClientImpl {

    public static void server(String host, int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());
            Channel channel = b.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String msg = in.readLine();
                channel.writeAndFlush(msg + "\n");
            }

        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        server("localhost", 10001);
    }
}
