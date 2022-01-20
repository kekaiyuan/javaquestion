package com.kky.juc;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Runnable runnable1 = new Runnable(){
            @Override
            public void run() {
                lock.lock();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName());
            }
        };
        Runnable runnable2 = new Runnable(){
            @Override
            public void run() {
                lock.lock();
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(Thread.currentThread().getName());
            }
        };
        for (int i = 0; i < 5; i++) {
            new Thread(runnable1, "t1-" + i).start();
            new Thread(runnable2, "t2-" + i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.notifyAll();
    }
}