package com.kyh.userAgentString;

/**
 * Created by kongyunhui on 2017/2/21.
 */
public class Test4 {
    public static void main(String[] args){
        // user agent string: request.getHeader("User-Agent")
        UserAgent userAgent = UserAgentUtil.getUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        System.out.println(userAgent);
    }
}
