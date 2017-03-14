package com.kyh.base;

import com.google.common.collect.Lists;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kongyunhui on 2017/3/6.
 */
public class Test {
    public static void main(String[] args){
        fun1();

        fun2();

        fun3();

        fun4();
    }

    /**
     * +和+=的区别
     */
    private static void fun1(){
        byte a = 1;
        byte b = 1;
//        a = a+b; // +会把byte，short先强转为int型，因此此处再赋值给a，类型转换错误！
        a+=b;
        System.out.println(a);
    }

    /**
     * 有些浮点数不能完全精确的表示出来
     */
    private static void fun2(){
        System.out.println(3*0.1);
        System.out.println(3*0.1 == 0.3); // false
    }

    /**
     * 软引用在设备内存比较少的时候特别有用，比如Android系统。
     */
    private static void fun3(){
        SoftReference userReference = new SoftReference(new User());
    }

    /**
     * 判断2个set，equal为true的条件: 2个引用指向同一个内存 或者  size1=size2 && set1.containAll(set2)
     */
    private static void fun4(){
        Set<String> set1 = new HashSet<String>();
        set1.addAll(Lists.newArrayList("1","2","3","1","2","3","4"));

        Set<String> set2 = new HashSet<String>();
        set2.addAll(Lists.newArrayList("1","2","3"));

        System.out.println(set1.equals(set2));
    }
}
