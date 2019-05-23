package com.kyh.fastjson;

import java.lang.reflect.Field;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;

/**
 * @author kongyunhui on 2018/1/30.
 */
public class Test {
    public static void main(String[] args) throws Exception {
//        User user = new User("kong", "123");
//        if(pojoValidator(user)){
//            System.out.println(user);
//        }

        String message = "{\"1\":\"1\",\"2\":\"2\"}";
        User user1 = JSONObject.parseObject(message, User.class);
        System.out.println(user1);
        completenessValidator(user1);

    }

    public static boolean completenessValidator(Object obj) throws IllegalAccessException{
        return completenessValidator(obj, null);
    }

    public static boolean completenessValidator(Object obj, List<String> ignoreFieldNames) throws IllegalAccessException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (ignoreFieldNames!=null && ignoreFieldNames.contains(f.getName())){
                continue;
            }
            if (f.get(obj) == null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                System.out.println(f.getName()+" is null, drop it");
                return false;
            }
        }
        return true;
    }
}
