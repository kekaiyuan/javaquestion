package com.kky.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    Lock lock = new ReentrantLock();
    Semaphore semaphore = new Semaphore(4);
}
