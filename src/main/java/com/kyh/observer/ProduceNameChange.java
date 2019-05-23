package com.kyh.observer;

/**
 * Created by kongyunhui on 2017/2/14.
 *
 * 观察者
 *
 * 1、订阅
 * 2、定义观察的属性
 * 3、封装行为
 */
public class ProduceNameChange extends ProductPropertyObserver {
    /**
     * 在构造函数中，给主题添加观察者
     */
    public ProduceNameChange(ProductPropertyObservable observable){
        observable.addObserver(this);
    }

    /**
     * 定义观察的属性
     */
    @Override
    String propertyName() {
        return "name";
    }

    @Override
    void process(String newValue) {
        System.out.println("the produce's newName is " + newValue);
    }
}
