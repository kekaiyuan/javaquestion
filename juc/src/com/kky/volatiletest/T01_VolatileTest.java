package com.kky.volatiletest;

/**
 * @author 柯凯元
 * @date 2021/07/16 16:12
 */

/*
 * volatile 关键字，使一个变量在多个线程间可见
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，并不会每次都去
 * 读取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 *
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 *
 * 可以阅读这篇文章进行更深入的理解
 * http://www.cnblogs.com/nexiyi/p/java_memory_model_and_thread.html
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 */

public class T01_VolatileTest {
    /*volatile*/ boolean running = true; //对比一下有无volatile的情况下，整个程序运行结果的区别
    void m() {
        System.out.println("m start");
        while(running) {
//            synchronized (this){
//
//            }

//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("666");
            //System.out.println(running);
        }
        System.out.println("m end!");
    }

    public static void main(String[] args) throws InterruptedException {
        T01_VolatileTest t = new T01_VolatileTest();
        new Thread(t::m, "t1").start();

        //睡眠一段时间是为了让线程 t 将 running = true 复制到工作内存中
        Thread.sleep(1000);

        //修改 running 的值，尝试让线程 t 停下来
        t.running = false;
        //System.out.println(t.running);
    }
}