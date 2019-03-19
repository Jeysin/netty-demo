package com.jeysin.TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: Jeysin
 * @Date: 2019/3/19 22:08
 * @Desc:
 */

public class TimeDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 4){
            return;
        }
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
    }
}
