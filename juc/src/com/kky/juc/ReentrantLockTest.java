package com.kky.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    Lock lock = new ReentrantLock();

    /**
     * 普通上锁 lock()
     */
    public void lock01(){
        try {
            lock.lock(); //上锁
			//业务逻辑
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 非阻塞式上锁 tryLock()
     */
    public void lock02(){
        boolean locked = false;
        try {
            //locked = lock.tryLock();
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            if (locked) {
                System.out.println("获得锁成功");
			    //成功逻辑
            } else {
                System.out.println("获得锁失败");
			    //失败逻辑
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    /**
     * 可响应外部中断上锁 lockInterruptibly()
     */
    public void lock03() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName()+" start");
                    Thread.sleep(Integer.MAX_VALUE);
                    System.out.println(Thread.currentThread().getName()+" end");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+" interrupted!");
                } finally {
                    lock.unlock();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();

        Thread t2 = new Thread(runnable);
        t2.start();

        Thread.sleep(5000);
        t1.interrupt(); //打断线程 1 的等待

        Thread.sleep(5000);
        t2.interrupt(); //打断线程 2 的等待
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.lock03();
    }
}


