package com.kyh.exception;

/**
 * @author kongyunhui on 2018/1/29.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        validate();
        System.out.println(11);
    }

    public static void validate() throws Exception{
        throw new Exception("error");
    }
}
