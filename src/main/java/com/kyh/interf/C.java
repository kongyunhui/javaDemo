package com.kyh.interf;

import java.io.IOException;

/**
 * @author kongyunhui on 2018/8/31.
 */
public interface C extends A, B {
    public static final int a = 1;

//    @Override
//    public void m(); // 重写A/B接口的方法，可以选择不抛出异常

    @Override
    public void m() throws IOException; // 如果选择抛出异常，则需要抛出A/B.m()的公共异常

    public void m(int i);
}
