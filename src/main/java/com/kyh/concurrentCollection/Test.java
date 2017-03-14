package com.kyh.concurrentCollection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kongyunhui on 2017/2/27.
 */
public class Test {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3); // 线程池

    private static final ConcurrentHashMap<String, String> firstCache = new ConcurrentHashMap<String, String>(); // 一级缓存 （全局，数据读取的统一入口）

    private static final ConcurrentHashMap<String, String> secondCache = new ConcurrentHashMap<String, String>(); // 二级缓存

    public static void main(String[] args){
        loadFirstCache(0);
        operation();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadFirstCache(10);
        operation();
    }

    private static void operation(){
        // 大循环
        for(int i=0; i<3;i++){
            final int tn = i;
            EXECUTOR_SERVICE.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1:"+firstCache);
                    System.out.println("2:"+secondCache);
                    ConcurrentHashMap<String, String> data = loadData(firstCache, secondCache, tn);
                    System.out.println(Thread.currentThread().getName() + "并发读取，并IO操作:" + data);
                }
            });
        }
    }

    private static void loadFirstCache(int i){
        // 模拟读取数据库操作
        ConcurrentHashMap<String, String> dbData = new ConcurrentHashMap<String, String>();
        int len = i+12;
        for(; i<len; i++){
            dbData.putIfAbsent(i+"", "name"+i);
        }
        if(firstCache==null || firstCache.isEmpty()){
            firstCache.putAll(dbData);
            return;
        }
        // 大循环下数据库变动，更新一级缓存
        if(!dbData.equals(firstCache)){
            firstCache.clear();
            firstCache.putAll(dbData);
            dbData.clear();
            // 异步修改二级缓存
            EXECUTOR_SERVICE.execute(new Runnable() {
                public void run() {
                    secondCache.clear();
                    secondCache.putAll(firstCache);
                }
            });
            // 删除一级缓存
            firstCache.clear();
        }
    }

    private static ConcurrentHashMap<String, String> loadData(ConcurrentHashMap<String, String> firstCache, ConcurrentHashMap<String, String> secondCache, int i){
        ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
        if(firstCache!=null && !firstCache.isEmpty()){
            int j=i*4;
            data.putIfAbsent(j+"", firstCache.get(j+"")); j++;
            data.putIfAbsent(j+"", firstCache.get(j+"")); j++;
            data.putIfAbsent(j+"", firstCache.get(j+"")); j++;
            data.putIfAbsent(j+"", firstCache.get(j+""));
        } else if(secondCache!=null && !secondCache.isEmpty()){
            int j=i;
            data.putIfAbsent(j+"", secondCache.get(j+"")); j++;
            data.putIfAbsent(j+"", secondCache.get(j+"")); j++;
            data.putIfAbsent(j+"", secondCache.get(j+"")); j++;
            data.putIfAbsent(j+"", secondCache.get(j+""));
        } else {
            System.out.println("empty data!");
        }
        return data;
    }
}
