package com.kyh.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author kongyunhui on 2018/8/28.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2); // 1

        for(int i=0; i<2; i++){
            new Thread(new Task(barrier)).start();
        }

        // 重用
        for(int i=0; i<2; i++){
            new Thread(new Task(barrier)).start();
        }
    }

    static class Task implements Runnable{
        private CyclicBarrier barrier;

        public Task(CyclicBarrier barrier){
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting...");
                Thread.sleep(10);
                barrier.await(); // 2
                System.out.println(Thread.currentThread().getName() + " ended");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
