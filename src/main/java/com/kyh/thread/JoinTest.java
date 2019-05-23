package com.kyh.thread;

/**
 * @author kongyunhui on 2018/8/30.
 */
public class JoinTest {
    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(new Task());

        System.out.println("我");

        thread.start();
        thread.join();

        System.out.println("中国");
    }

    static class Task implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("爱");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
