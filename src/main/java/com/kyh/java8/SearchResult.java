package com.kyh.java8;

/**
 * Created by kongyunhui on 2017/7/6.
 */
public class SearchResult {
    private String result;

    public SearchResult(String result){
        this.result = result;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "result='" + result + '\'' +
                '}';
    }
}
