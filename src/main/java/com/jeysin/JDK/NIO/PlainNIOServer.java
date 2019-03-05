package com.jeysin.JDK.NIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @Author: Jeysin
 * @Date: 2019/3/5 20:51
 * @Desc:
 */

public class PlainNIOServer {
    public void server(Integer port) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        socket.bind(address);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi, jeysin, 你好".getBytes(Charset.forName("UTF-8")));
        while(true){
            selector.select();
            Set<SelectionKey> readKeys = selector.selectedKeys();
            for(SelectionKey selectionKey : readKeys){
                //记得要remove这个Set里面的selectionKey，否则会重复触发selector，造成空指针异常
                readKeys.remove(selectionKey);

                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                    System.out.println("Accept connection from " + client);
                }
                if(selectionKey.isWritable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    while(buffer.hasRemaining()){
                        if(client.write(buffer)==0){
                            break;
                        }
                    }
                    client.close();
                }
            }
        }
    }

    //或者另一种写法， 不利用selectionKey的附加数据即attachment
    public void server2(Integer port) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        socket.bind(address);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            selector.select();
            Set<SelectionKey> readKeys = selector.selectedKeys();
            for(SelectionKey selectionKey : readKeys){
                //记得要remove这个Set里面的selectionKey，否则会重复触发selector，造成空指针异常
                readKeys.remove(selectionKey);

                if(selectionKey.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    System.out.println("Accept connection from " + client);
                }
                if(selectionKey.isWritable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer msg = ByteBuffer.wrap("Hi, jeysin, 你好".getBytes(Charset.forName("UTF-8")));
                    while(msg.hasRemaining()){
                        if(client.write(msg)==0){
                            break;
                        }
                    }
                    client.close();
                }
            }
        }
    }
    public static void main(String[] args) throws Exception{
        PlainNIOServer plainNIOServer = new PlainNIOServer();
        plainNIOServer.server(10001);
    }
}
