package com.kyh.disanfanglibs;

import com.google.common.collect.ComparisonChain;

/**
 * Created by kongyunhui on 2017/2/20.
 */
public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // 优雅的comparable实现
    public int compareTo(Person that) {
        return ComparisonChain.start()
                .compare(this.name, that.name)
                .compare(this.age, that.age)
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Person){
            Person that = (Person)obj;
            if(this.compareTo(that) == 0) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
