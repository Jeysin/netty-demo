package com.jeysin.reflection;

import com.jeysin.io.File.FileDemo;
import com.jeysin.io.pojo.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @Author: Jeysin
 * @Date: 2019/4/6 20:57
 * @Desc:
 */

public class ReflectionDemo {

    private static void ConstructorTest() throws Exception{
        Class clz = Student.class;
        Constructor[] constructors = clz.getConstructors();
        Constructor constructor = clz.getConstructor(String.class, int.class);
        Class[] parameterTypes = constructor.getParameterTypes();
        for(Class parameterType : parameterTypes){
            System.out.println(parameterType.getName());
        }
        Student stu1 = (Student) constructor.newInstance("jeysin", 20);
        System.out.println(stu1.getName()+":"+stu1.getAge());
    }

    private static void FieldTest() throws Exception{
        Field[] fields = Student.class.getDeclaredFields();
        for(Field field : fields){
            System.out.println(field.getName());
        }
    }

    private static void ArrayTest() throws Exception{
        Class clz = Class.forName("[I");
        System.out.println(clz);
    }

    public static void main(String[] args) throws Exception{
        //ConstructorTest();
        //FieldTest();
        ArrayTest();
    }
}
