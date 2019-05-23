package com.kyh.interf;

/**
 * @author kongyunhui on 2018/8/31.
 */
public class Test extends D implements C {
    public static void main(String[] args) {
        try {
            C c = new Test();
            c.m();
            c.m(1);

            D d = new Test();
            d.m1();
            d.m2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void m() {
        System.out.println("c.m()");
    }

    @Override
    public void m(int i) {
        System.out.println("c.m(i)");
    }

    @Override
    protected void m1() throws Exception {
        System.out.println("c.m1");
    }

    @Override
    protected void m2() {
        System.out.println("c.m2");
    }
}
