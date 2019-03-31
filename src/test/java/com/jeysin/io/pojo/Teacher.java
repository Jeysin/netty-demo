package com.jeysin.io.pojo;

import java.io.Serializable;

/**
 * @Author: Jeysin
 * @Date: 2019/3/30 23:40
 * @Desc:
 */

public class Teacher implements Serializable {

    private static final long serialVersionUID = -425929511943537438L;

    private String name;

    private int age;

    private String address;

    public Teacher(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
