package com.kky.volatiletest;

/**
 * @author 柯凯元
 * @date 2021/07/16 19:45
 */

/*
使用 synchronized 保证数据同步
 */
public class T05_VolatileVsSync {
    int count = 0;

    synchronized void m() {
        for (int i = 0; i < 10000; i++)
            count++;
    }

    public static void main(String[] args) throws InterruptedException {
        T05_VolatileVsSync t = new T05_VolatileVsSync();

        for (int i = 0; i < 10; i++) {
            new Thread(t::m).start();
        }

        //等待线程结束
        Thread.sleep(3000);

        System.out.println(t.count);
    }
}
