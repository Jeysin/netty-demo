package com.jeysin.io.Stream;

import java.io.*;

/**
 * @Author: Jeysin
 * @Date: 2019/3/26 20:35
 * @Desc:
 */

public class ByteArrayInputStreamDemo {

    private static void ByteArrayInputStreamTest() throws Exception{
        InputStream input = new ByteArrayInputStream("Hello World, jeysin".getBytes());
        try {
            byte[] bytes = new byte[4];
            int length;
            while((length = input.read(bytes)) != -1){
                System.out.println(length);
                System.out.println(new String(bytes, 0, length));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void CharArrayReaderTest() {
        Reader reader = new CharArrayReader("Hello World, jeysin".toCharArray());
        try{
            char[] chars = new char[4];
            int length;
            while((length = reader.read(chars)) != -1){
                System.out.println(length);
                System.out.println(new String(chars, 0, length));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void ByteArrayOutputStreamTest() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try{
            output.write("Hello World, jeysin".getBytes());
            output.write("how are you".getBytes());
            byte[] bytes = output.toByteArray();
            System.out.println(new String(bytes));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void CharArrayWriterTest(){
        CharArrayWriter writer = new CharArrayWriter();
        try{
            writer.write("Hello World, jeysin".toCharArray());
            writer.write("how are you".toCharArray());
            char[] chars = writer.toCharArray();
            System.out.println(chars);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        ByteArrayInputStreamTest();
        System.out.println();
        CharArrayReaderTest();
        System.out.println();
        ByteArrayOutputStreamTest();
        System.out.println();
        CharArrayWriterTest();
    }
}
