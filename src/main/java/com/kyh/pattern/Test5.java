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

        matchReplaceStr();

        matchEmail();

        matchPassword();

        matchSplit();

        matchDynamicResource();
    }

    /**
     * 匹配电话号码，如(212) 555-1212， 212 555-1212， 212 555 1212， 212 5551212
     * 圆括号有2种用法：
     * 1、简单字符：//(
     * 2、组合类型：(-|)
     *
     * \d：0-9的数字
     * \s: 空格
     * \: \\
     * $: 表示字符串结束，此处表示以4个数字结束
     */
    private static void matchPhone(){
        String str = "(212) 555-1212";
        Pattern p = Pattern.compile("(\\(\\d{3}\\)|\\d{3})\\s?\\d{3}(-| )?\\d{4}$");
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(str + "是电话号码");
        }
    }

    /**
     * 匹配连续重复的单词
     * \b: 匹配单词的边界，它可以是空格或任何一种不同的标点符号(包括逗号，句号等)
     * \w: 单独字符 [a-zA-Z_0-9] 数字、字母、下划线中的一个字符
     *  +: 匹配一次或多次字符
     * \1: 向后扫描。指的是任何被\w+所匹配的单词
     *
     * Pattern.CASE_INSENSITIVE: 非大小写敏感
     */
    private static void matchReplaceStr(){
        String str = "This is a words. The the theme of this article is is the Java's regex package.";
        Pattern p =Pattern.compile("\\b(\\w+)\\s+\\1\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }

    /**
     * 匹配Email
     */
    private static void matchEmail(){
        String str = "1032316751@qq.com";
        Pattern p = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(str + " is a email");
        }
    }

    /**
     * 验证用户密码。以字母开头，长度在6~18之间，只能包含字符、数字和下划线
     */
    private static void matchPassword(){
        String str = "a1a_2b";
        Pattern p = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
        Matcher matcher = p.matcher(str);
        if(matcher.find()){
            System.out.println(str + " is a valid password");
        }
    }

    /**
     *
     * 以正则表达式为界，将字符串分割成String数组
     */
    private static void matchSplit(){
        String str = "我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com";
        Pattern p = Pattern.compile("\\d+");
        String[] split = p.split(str);
        for(int i=0;i<split.length;i++){
            System.out.println(split[i]);
        }
    }

    /**
     * 匹配非静态资源
     * .*： 代表任意字符串
     * 第一个()是.css的组合类型。没有|分割表示同时出现
     * 第二个()是css|js的组合类型
     */
    private static void matchDynamicResource(){
        String str = "http://www.baidu.com/";
        Pattern FILTER = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
        if(!FILTER.matcher(str).matches()){
            System.out.println(str + " is a link of dynamic resource");
        }
    }
}
