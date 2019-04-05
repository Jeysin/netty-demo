package com.jeysin.nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.rmi.server.ExportException;

/**
 * @Author: Jeysin
 * @Date: 2019/4/3 22:12
 * @Desc:
 */

public class ChatClientDemo {

    public static void ChatClient_V1(String host, int port){
        try(SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.connect(new InetSocketAddress(host, port));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while(!(line = reader.readLine()).equals("exit")){
                socketChannel.write(ByteBuffer.wrap(line.getBytes()));
                ByteBuffer rBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(rBuffer);
                System.out.println(new String(rBuffer.array()));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ChatClient_V1("localhost", 10001);
    }
}
