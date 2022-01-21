package com.kky.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    Lock lock = new ReentrantLock();

    /**
     * 普通上锁 lock()
     */
    public void lock01() {
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
    public void lock02() {
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
        Runnable runnable = () -> {
            try {
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + " start");
                Thread.sleep(Integer.MAX_VALUE);
                System.out.println(Thread.currentThread().getName() + " end");
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted!");
            } finally {
                lock.unlock();
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

    /**
     * 实现 Condition 变量实现线程同步
     */
    public void condition() throws InterruptedException {
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Runnable runnable1 = () -> {
            lock.lock();
            try {
                condition1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName());
        };
        Runnable runnable2 = () -> {
            lock.lock();
            try {
                condition2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName());
        };
        for (int i = 0; i < 5; i++) {
            new Thread(runnable1, "t1-" + i).start();
            new Thread(runnable2, "t2-" + i).start();
        }
        Thread.sleep(1000);
        lock.lock();
        condition1.signalAll();
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        reentrantLockTest.condition();
    }
}


