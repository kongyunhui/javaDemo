package com.kyh.extend;

/**
 * Created by kongyunhui on 2017/11/22.
 */
public class Test {
    public static void main(String[] args){
        Parent p = new Parent();
        p.doService(); // 只要是"父类对象"进行方法调用，一定是优先使用父类中重写的方法（即使此时是在子类方法域中）。

        Base b = new Base();
        b.doService();
    }
}
