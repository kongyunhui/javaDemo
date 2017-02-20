package com.kyh.wordsegmenter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;


/**
 * Created by kongyunhui on 2017/2/15.
 *
 * 导入lib + dic文件
 *
 * 引入Lucene所需的jar文件和IKAnalyzer2012.jar文件，把stopword.dic和IKAnalyzer.cfg.xml复制到class根目录，建立一个扩展词典ext.dic和中文停用词词典chinese_stopword.dic
 */
public class Test3 {
    public static void main(String[] args){
        // 为什么过滤了"年"，依然显示"1992年"?
        // 因为解析器认为"1992年"是一个单词。因此只想显示1992，则在ext.dic中扩展"1992"作为一个单词。
        String text = "我是一个1992年出生的程序员，这是基于java语言开发的轻量级的中文分词工具包。";
        try {
            // 创建analyzer
            Analyzer analyzer = new IKAnalyzer(true); // true 开启智能分词， false 最细粒度分词
            StringReader reader = new StringReader(text);
            // 生成tokenStream （token相当于cursor）
            TokenStream token = analyzer.tokenStream("", reader);
            CharTermAttribute attr = token.getAttribute(CharTermAttribute.class); // 获得token的属性 - 词
            // 移动tokenSteam
            while (token.incrementToken()) {
                System.out.print(attr.toString()+" ");
            }
            reader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
