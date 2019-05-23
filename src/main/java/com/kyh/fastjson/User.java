package com.kyh.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import com.drew.lang.annotations.NotNull;

/**
 * @author kongyunhui on 2018/1/30.
 */
public class User {
    private String username;
    private String password;
    private String age;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
