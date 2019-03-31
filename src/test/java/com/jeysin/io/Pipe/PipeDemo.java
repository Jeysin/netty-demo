package com.jeysin.io.Pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @Author: Jeysin
 * @Date: 2019/3/26 18:38
 * @Desc:
 */

public class PipeDemo {

    private static void PipedInputStreamTest() throws Exception{
        PipedOutputStream output = new PipedOutputStream();
        PipedInputStream input = new PipedInputStream(output);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    output.write("Hello World, pipe!".getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    byte[] bytes = new byte[1024];
                    int length = input.read(bytes);
                    System.out.println(length);
                    System.out.println(new String(bytes));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) throws Exception{
        PipedInputStreamTest();
    }
}
