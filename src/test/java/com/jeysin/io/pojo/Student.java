package com.jeysin.io.pojo;

import java.io.Serializable;

/**
 * @Author: Jeysin
 * @Date: 2019/3/30 23:03
 * @Desc:
 */

public class Student implements Serializable{

    private static final long serialVersionUID = -7407530951600463672L;

    private String name;

    private int age;

    public Student(){

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
