package com.jeysin.ChatServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: Jeysin
 * @Date: 2019/3/21 15:54
 * @Desc:
 */

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for(Channel channel : channels){
            if(!incoming.equals(channel)){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]:" + s + "\n");
            }else{
                channel.writeAndFlush("[you]:" + s + "\n");
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChatClient:" + ctx.channel().remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ChatClient:" + ctx.channel().remoteAddress() + "掉线");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER]:" + incoming.remoteAddress() + "加入\n");
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[SERVER]:" + incoming.remoteAddress() + "离开\n");
        //A closed Channel is automatically removed from ChannelGroup,
        //so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ChatClient:" + ctx.channel().remoteAddress() + "异常");
        cause.printStackTrace();
        ctx.close();
    }
}
