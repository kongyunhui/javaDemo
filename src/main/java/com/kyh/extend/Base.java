package com.kyh.extend;

/**
 * Created by kongyunhui on 2017/11/22.
 */
public class Base {
    void doService(){
        doBefore();
    }

    void doBefore(){
        System.out.println("doBefore from base");
    }
}
