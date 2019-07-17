package com.example;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


    class MyObject {
        volatile int incrementInteger = 0;
        final Object monitor = new Object();

        final ReentrantLock lock = new ReentrantLock();
    }

    class CounterThread implements Runnable {
        MyObject res;

        CounterThread(MyObject res) {
            this.res = res;
        }


        @Override
        public void run() {
            res.lock.lock();
            try {
                res.incrementInteger++;
                System.out.println(res.incrementInteger);
            } finally {
                res.lock.unlock();
            }

//        synchronized (res.monitor) {
//            res.incrementInteger++;
//            System.out.println(res.incrementInteger);
//        }

        }
    }

    public class Task2 {
        static final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        public static void main(String[] args) {
            MyObject commonObject = new MyObject();
            for (int i = 0; i < 20; i++) {
                executor.execute(new CounterThread(commonObject));
            }
        }
    }