package com.kyh.pattern;

import java.util.regex.*;

/**
 * Created by kongyunhui on 2017/2/21.
 *
 * JDK 1.4提供的java.util.regex包，用于处理正则表达式，主要包含：
 * Pattern：定义匹配模式
 * Matcher：匹配目标
 */
public class Test5 {
    public static void main(String[] args){
        matchPhone();

        matchStr1();

        matchStr2();
    }

    /**
     * 匹配电话号码，如(212) 555-1212， 212 555-1212， 212 555 1212， 212 5551212
     * 圆括号有2种用法：
     * 1、简单字符：//(
     * 2、组合类型：(-|)
     *
     * /d：0-9的数字
     * /s: 空格
     * //: /
     */
    private static void matchPhone(){
        String str = "(212)";
        Pattern p = Pattern.compile("(//(//d{3}//)|//d{3})");
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(str + "是电话号码");
        }
    }

    /**
     * ^：任何以100或者110开头的字符串
     */
    private static void matchStr1(){
        String str = "11023456";
        Pattern p = Pattern.compile("(^100|^110)");
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(str + "是100或者110开头");
        }
    }

    /**
     * 匹配重复的单词
     * /b: 匹配单词的边界，它可以是空格或任何一种不同的标点符号(包括逗号，句号等)
     * /w: 匹配从字母a到u的任何字符
     *  +: 匹配一次或多次字符
     *
     * Pattern.CASE_INSENSITIVE: 大小写敏感
     */
    private static void matchStr2(){
        String str = "The the theme of this article is the Java's regex package.";
        Pattern p =Pattern.compile("//b(//w+)//s+//1//b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
