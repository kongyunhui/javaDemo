package com.kyh.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author kongyunhui on 2018/8/28.
 */
public class ExecutorServiceTest {
    public static void main(String[] args){
        ExecutorService pools = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Task task = new Task();
        Future<Integer> future = pools.submit(task); // 除了配合submit来获取Future对象，还可以使用FutureTask
        pools.shutdown();

        System.out.println("主线程在执行任务");

        try {
            Integer integer = future.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("主线程结束执行任务");
    }

    static class Task implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            return 100;
        }
    }
}
