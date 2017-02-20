package com.kyh.observer;

/**
 * Created by kongyunhui on 2017/2/14.
 *
 * https://www.diycode.cc/topics/590
 */
public class Test2 {
    public static void main(String[] args){
        // 创建Observable（主题）
        ProductPropertyObservable observable = new ProductPropertyObservable();
        // 创建Observer  （订阅主题）
        new ProduceStateChange(observable);
        new ProduceNameChange(observable);
        // 属性变化，观察者们动起来 （state选择观察者集合，state-1作为参数传递）
        observable.propertyChange("state", "state-1");
        observable.propertyChange("name", "name-1");
    }
}
