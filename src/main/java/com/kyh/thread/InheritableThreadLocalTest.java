package com.kyh.thread;

/**
 * Created by kongyunhui on 2017/11/22.
 */
public class InheritableThreadLocalTest {
    public static void main(String[] args) throws Exception{
        BusinessContextHolder.setContextHolder("main");
        new Thread(new RunnableA()).start();
        Thread.sleep(5000);
        System.out.println(BusinessContextHolder.getContextHolder());
        BusinessContextHolder.removeContextHolder();
    }

    static class BusinessContextHolder {
        private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<String>();

        public static String getContextHolder(){
            return contextHolder.get();
        }

        public static void setContextHolder(String str){
            contextHolder.set(str);
        }

        public static void removeContextHolder(){
            contextHolder.remove();
        }
    }

    static class RunnableA implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":" + BusinessContextHolder.getContextHolder());
            BusinessContextHolder.setContextHolder("sub");
        }
    }
}




