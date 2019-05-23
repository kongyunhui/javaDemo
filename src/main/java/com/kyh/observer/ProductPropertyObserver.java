package com.kyh.observer;

/**
 * Created by kongyunhui on 2017/2/14.
 *
 * 产品观察者的抽象类
 */
public abstract class ProductPropertyObserver {
    /**
     * 观察的属性
     * @return
     */
    abstract String propertyName();

    /**
     * 处理逻辑
     * @param newValue
     */
    abstract void process(String newValue);

    /**
     * 观察者接收消息，执行变更
     * @param observable
     * @param newValue
     */
    public void update(ProductPropertyObservable observable, String newValue){
        process(newValue);
    }
}
