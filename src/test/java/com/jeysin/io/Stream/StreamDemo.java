package com.jeysin.io.Stream;

import com.jeysin.io.pojo.Student;
import com.jeysin.io.pojo.Teacher;

import java.io.*;

/**
 * @Author: Jeysin
 * @Date: 2019/3/26 21:45
 * @Desc:
 */

public class StreamDemo {

    private static void SystemIOStreamTest(){
        InputStream input = System.in;
        try{
            byte[] bytes = new byte[4];
            int length;
            while((length = input.read(bytes)) != -1){
                String str = new String(bytes, 0, length);
                if(str.equals("exit")){
                    break;
                }
                System.out.print(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void SystemIOStreamTest2(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            while ((str = reader.readLine()) != null) {
                if (str.equals("exit")) {
                    break;
                }
                System.out.println(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void SerializableTest(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("D:\\somefiles\\data.txt"));
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\somefiles\\data.txt"))){
            Student stu = new Student("jeysin", 20);
            output.writeObject(stu);
            output.writeObject(new Student("jiaxian", 21));
            output.writeObject(new Teacher("yize", 34, "hangzhou"));
            output.flush();

            Student stu1 = (Student) input.readObject();
            Student stu2 = (Student) input.readObject();
            Teacher tea = (Teacher) input.readObject();

            System.out.println(stu == stu1);
            System.out.println(stu1.getName()+":"+stu1.getAge());
            System.out.println(stu2.getName()+":"+stu2.getAge());
            System.out.println(tea.getName()+":"+tea.getAge()+":"+tea.getAddress());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void SerializableTest2(){
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("D:\\somefiles\\data.txt"))){
            Student stu = (Student) input.readObject();
            System.out.println(stu.getName()+":"+stu.getAge());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //SystemIOStreamTest();
        //SystemIOStreamTest2();
        //SerializableTest();
        SerializableTest();
    }
}
