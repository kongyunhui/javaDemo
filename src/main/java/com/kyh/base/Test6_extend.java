package com.kyh.base;

/**
 * Created by kongyunhui on 2017/2/24.
 */
public class Test6_extend {
    public static void main(String[] args){
        A a = new A();
        B b = new B();
        a = (A) b; // 子类强制转化成父类 true

        A a1 = new A();
        B b1 = new B();
        b1 = (B) a1; // 父类强制转化成子类 FALSE

    }
}

class A{}
class B extends A{}
