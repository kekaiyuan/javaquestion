package com.kky.volatiletest;

/**
 * @author 柯凯元
 * @date 2021/07/16 20:02
 */

/**
 * volatile 引用类型（包括数组）只能保证引用本身的可见性，不能保证内部字段的可见性
 */

public class T02_VolatileReference1 {

    boolean[] running = new boolean[]{true};

    void m() {
        System.out.println("m start");
        while (running[0]) {
        }
        System.out.println("m end!");
    }

    public static void main(String[] args) throws InterruptedException {
        T02_VolatileReference1 t = new T02_VolatileReference1();
        new Thread(t::m, "t1").start();

        Thread.sleep(1000);

        t.running[0] = false;
    }
}

