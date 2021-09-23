package com.kky.threadloacl;

public class ThreadLocalTest {
    static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Person p = new Person();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(new Person());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadLocal.get());
            }
        }).start();
    }
}

class Person{
    String name="zhangsan";
}