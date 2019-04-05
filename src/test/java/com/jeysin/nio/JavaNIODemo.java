package com.jeysin.nio;

import javax.xml.crypto.Data;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: Jeysin
 * @Date: 2019/3/31 15:50
 * @Desc:
 */

public class JavaNIODemo {

    private static void ChannelDemo(){
        try(RandomAccessFile aFile = new RandomAccessFile("D:\\somefiles\\data.txt", "rw")){
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int length;
            while((length = fileChannel.read(buf)) != -1){
                System.out.println(length);
                buf.flip();
                while(buf.hasRemaining()){
                    System.out.print((char) buf.get());
                }
                buf.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void scatterGatherTest(){
        try(FileInputStream inputStream = new FileInputStream("D:\\somefiles\\data.txt")){
            FileChannel fileChannel = inputStream.getChannel();
            ByteBuffer header = ByteBuffer.allocate(4);
            ByteBuffer body = ByteBuffer.allocate(8);
            ByteBuffer[] bufferArray = {header, body};
            long length = fileChannel.read(bufferArray);
            System.out.println(length);
            System.out.println(new String(header.array()));
            System.out.println(new String(body.array()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void FileChannelTest(){
        try(RandomAccessFile fromFile = new RandomAccessFile("D:\\somefiles\\data.txt", "rw");
            RandomAccessFile toFile = new RandomAccessFile("D:\\somefiles\\data2.txt", "rw")){
            FileChannel fromChannel = fromFile.getChannel();
            FileChannel toChannel = toFile.getChannel();
            toChannel.transferFrom(fromChannel, fromChannel.position(), fromChannel.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void SocketChannelTest(){
        try(SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.connect(new InetSocketAddress(10001));
            InputStream input = System.in;
            byte[] bytes = new byte[1024];
            int length = input.read(bytes);
            socketChannel.write(ByteBuffer.wrap(bytes, 0, length));

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socketChannel.read(buffer);
            System.out.println(new String(buffer.array()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void ServerSocketChannelTest(){
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            serverSocketChannel.socket().bind(new InetSocketAddress(10001));
            while(true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                ByteBuffer buf = ByteBuffer.allocate(1024);
                int length = socketChannel.read(buf);
                buf.flip();
                System.out.println(length);
                System.out.println(new String(buf.array()));

                socketChannel.write(ByteBuffer.wrap(String.valueOf(length).getBytes()));

                socketChannel.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void DatagramChannelTest(){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try(DatagramChannel datagramChannel = DatagramChannel.open()){
                    datagramChannel.socket().bind(new InetSocketAddress(10002));
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    System.out.println("Thread1: listenning...");
                    SocketAddress socketAddress = datagramChannel.receive(buf);

                    //System.out.println在多线程并发环境下会出现问题，打印出奇怪的结果
                    System.out.println("Thread1: Received "+new String(buf.array())+" from "+socketAddress);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try(DatagramChannel channel = DatagramChannel.open()){
                    ByteBuffer buf = ByteBuffer.wrap("Hello, jeysin".getBytes());
                    int length = channel.send(buf, new InetSocketAddress("localhost", 10002));
                    System.out.println("Thread2: message be sent");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        ThreadSleep(2);
        thread2.start();
    }

    private static void ThreadSleep(int seconds){
        try{
            Thread.sleep(seconds * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static volatile boolean flag = true;

    private static void VolatileTest(){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread1: I am run");
                while(flag){

                }
                System.out.println("Thread1: I am break!");
            }
        });

        thread1.start();
        ThreadSleep(2);
        flag = false;
        System.out.println("MainThread: flag is false");
    }

    public static void main(String[] args){
        //ChannelDemo();
        //scatterGatherTest();
        //SocketChannelTest();
        //FileChannelTest();
        //ServerSocketChannelTest();
        //DatagramChannelTest();
        //VolatileTest();
    }
}
