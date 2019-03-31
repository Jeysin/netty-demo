package com.jeysin.io.File;

import java.io.*;

/**
 * @Author: Jeysin
 * @Date: 2019/3/26 18:32
 * @Desc:
 */

public class FileDemo {

    private static void FileInputStreamTest() {
        InputStream input = null;
        try{
            input = new FileInputStream("D:\\somefiles\\data.txt");
            byte[] bytes = new byte[4];
            int length;
            while((length = input.read(bytes)) != -1){
                System.out.print(new String(bytes, 0, length));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(input != null){
                try {
                    input.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void FileInputStreamTest2(){
        try(InputStream input = new FileInputStream("D:\\somefiles\\data.txt")){
            byte[] bytes = new byte[4];
            int length;
            while((length = input.read(bytes)) != -1){
                System.out.print(new String(bytes, 0, length));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void FileOutputStreamTest(){
        try(OutputStream output = new FileOutputStream("D:\\somefiles\\data.txt")){
            PrintStream printStream = new PrintStream(output);
            System.setOut(printStream);
            System.out.println("abcdefg");
            System.out.println("hijklmn");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void RandomAccessFileTest(){
        try(RandomAccessFile file = new RandomAccessFile("D:\\somefiles\\data.txt", "rw")){
            file.seek(3);
            long pointer = file.getFilePointer();
            System.out.println(pointer);

            byte[] bytes = new byte[3];
            int length = file.read(bytes);
            System.out.println(new String(bytes, 0, length));
            System.out.println(file.getFilePointer());

            file.write("jiangjiaxian".getBytes());
            System.out.println(file.getFilePointer());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void FileTest(){
        File file = new File("D:\\somefiles\\data.txt");
        System.out.println(file.exists());
        System.out.println(file.length());
        System.out.println(file.isDirectory());
        System.out.println(file.getPath());

        File file2 = new File("D:\\Downloads");
        System.out.println(file2.isDirectory());
        String[] fileNames = file2.list();
        for(String fileName : fileNames){
            System.out.println(fileName);
        }
    }

    public static void main(String[] args) {
        //FileInputStreamTest();
        //System.out.println();
        //FileInputStreamTest2();
        //FileOutputStreamTest();
        //RandomAccessFileTest();
        //FileTest();
    }
}
