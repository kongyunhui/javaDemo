package com.kyh.interf;

/**
 * @author kongyunhui on 2018/9/3.
 */
public abstract class D {
    D(){}

    int i=0;

    abstract void m1() throws Exception;

    void m2(){
        System.out.println("d.m2");
    }

    public static void main(String[] args){
        System.out.println("main");
    }
}
