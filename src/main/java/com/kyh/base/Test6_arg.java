package com.kyh.base;

import sun.awt.SunToolkit;

/**
 * Created by kongyunhui on 2017/2/23.
 *
 * 引用传递: 传递的是对象的引用。2个引用指向同一个内存，其中一个改变，都是改变内存中数据本身。
 * 值传递：  传递的是值的拷贝。  2个变量无关联。
 */
public class Test6_arg {
    public static void main(String[] args){
        int i = 0;
        User u = new User();
        String a = "A";
        change(i, u, a);
        System.out.println(i);
        System.out.println(u);
        System.out.println(a);
    }

    /**
     * 方法被调用时，方法中的参数相当于执行了一次'赋值操作(=)'
     *
     * 注意：方法中的参数与main中的变量是2个不同的变量。即次i非彼i。但是引用传递，2个变量指向同一个内存。
     *
     * =: 赋值操作
     * 1、基本数据类型：  =操作是复制'变量的值'， =之后，左右变量'无关联'。
     * 2、非基本数据类型：=操作是复制'变量的引用'，=之后，左右变量'同时指向右边变量的引用'。
     */
    private static void change(int i, User u, String a){
        i = 1; // 值传递，此i与彼i无关联

        u.setName("kongyunhui"); // 引用传递，此u与彼u指向同一个内存空间。

        a = "B"; // a = new String("B"); // 引用传递，此a与彼a同时指向"A"内存空间。但是此a重新指向了"B"内存空间
    }
}
