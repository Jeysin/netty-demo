package com.jeysin.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ExportException;
import java.util.Set;

/**
 * @Author: Jeysin
 * @Date: 2019/3/31 22:58
 * @Desc:
 */

public class ChatServerDemo {

    public static void ChatServer_V1(int port){
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(10001));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey selectionKey : selectionKeys){
                    selectionKeys.remove(selectionKey);
                    if(selectionKey.isAcceptable()){
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("Accept connection from " + client);
                    }
                    if(selectionKey.isReadable()){
                        ByteBuffer rBuffer = ByteBuffer.allocate(1024);
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        int length = 0;
                        try {
                            length = client.read(rBuffer);
                        } catch (Exception e){
                            //当客户端没有调用close方法就强行关闭连接时，这里的read方法会抛出一个异常，length维持初始值0不变
                            //System.out.println("远程连接强行中断：" + client);
                        }
                        if(length > 0) {
                            ByteBuffer wBuffer = ByteBuffer.wrap(String.valueOf(length).getBytes());
                            while (wBuffer.hasRemaining()) {
                                client.write(wBuffer);
                            }
                        }else{
                            //length == 0 说明客户端被强行关闭，还没来得及调用close
                            //length == -1说明客户端调用close关闭了连接
                            //两种情况放在一起就是 length <= 0
                            System.out.println("远程连接中断：" + client);
                            client.close();
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ChatServer_V1(10001);
    }
}
