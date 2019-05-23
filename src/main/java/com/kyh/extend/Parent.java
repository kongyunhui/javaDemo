package com.kyh.extend;

/**
 * Created by kongyunhui on 2017/11/22.
 */
public class Parent extends Base {
    @Override
    void doBefore(){
        System.out.println("doBefore from Parent");
    }
}
