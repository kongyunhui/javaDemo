package com.kyh.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kongyunhui on 2017/2/14.
 *
 * 产品被观察者，也就是主题
 *
 * 1、当属性变更时通知订阅者响应
 * 2、添加观察者，并根据观察的属性不同进行分类
 */
public class ProductPropertyObservable {
    // 属性 - 观察者集合
    private Map<String, List<ProductPropertyObserver>> observerMap = new HashMap<String, List<ProductPropertyObserver>>();

    // 属性变更
    public void propertyChange(String propertyName, String newValue){
        notifyObservers(propertyName, newValue);
    }

    // 通知观察者
    private void notifyObservers(String propertyName, String newValue){
        // 遍历通知该属性对应的观察者集合
        List<ProductPropertyObserver> observers = observerMap.get(propertyName);
        if(observers!= null && !observers.isEmpty()){
            for(ProductPropertyObserver observer : observers){
                observer.update(this, newValue);
            }
        }
    }

    // 添加观察者(线程安全)
    public synchronized void addObserver(ProductPropertyObserver observer){
        // 给该观察者分类，进入相应的观察者集合
        List<ProductPropertyObserver> observers = observerMap.get(observer.propertyName());
        if(observers == null){
            observers = new ArrayList<ProductPropertyObserver>(); // 如果该观察者观察的属性首次被观察，则新建一个观察者集合
            observerMap.put(observer.propertyName(), observers);
        }
        if(!observers.contains(observer)){
            observers.add(observer); // 如果观察者集合已经包含了该观察者，就忽略
        }
    }
}
