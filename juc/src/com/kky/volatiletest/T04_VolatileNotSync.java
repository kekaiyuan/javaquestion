package com.kky.volatiletest;

/**
 * @author 柯凯元
 * @date 2021/07/16 16:18
 */
/*
创建 10 个线程，每个线程在 count 上执行 10000 次 ++ 操作
预期输出是 count = 100000
在没有锁的情况下，实际输出是多少？
*/
public class T04_VolatileNotSync {
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) count++;
    }

    public static void main(String[] args) throws InterruptedException {
        T04_VolatileNotSync t = new T04_VolatileNotSync();

        for (int i = 0; i < 10; i++) {
            new Thread(t::m).start();
        }

        //等待线程结束
        Thread.sleep(3000);

        System.out.println(t.count);
    }
}
