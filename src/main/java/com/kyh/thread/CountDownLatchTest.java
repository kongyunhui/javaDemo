package com.kyh.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author kongyunhui on 2018/8/28.
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws Exception{
        int n=2;
        CountDownLatch downLatch = new CountDownLatch(n); // 1
        for(int i=0; i<n; i++){
            new Thread(new Task(downLatch)).start();
        }
        downLatch.await(); // 3：主程序在这等待，任务书归0，继续执行
        System.out.println("main do something...");
    }

    static class Task implements Runnable{
        private CountDownLatch downLatch;

        public Task(CountDownLatch downLatch){
            this.downLatch = downLatch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            downLatch.countDown(); // 2：任务数-1
        }
    }
}
