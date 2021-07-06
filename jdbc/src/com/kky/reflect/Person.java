package com.kky.reflect;

/**
 * @author 柯凯元
 * @date 2021/07/05 19:41
 */
public class Person {
    public String name;
    public int age;

    public Person(){}

    public Person(String name, int age) {
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
