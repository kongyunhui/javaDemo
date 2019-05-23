package com.kyh.test;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.kyh.jsonpath.JsonPathUtil;

/**
 * Created by kongyunhui on 2017/12/14.
 */
public class BaseTest {
    @Test
    public void test1() {
        fun();
        System.out.println(1);
    }

    @Test
    public void test2() {
        ArrayList<Integer> oldEnvs = Lists.newArrayList(1, 2, 3, 5);
        ArrayList<Integer> newEnvs = Lists.newArrayList(2, 3, 4);
        for (Integer oldEnv : oldEnvs) {
            boolean foundInNew = false;
            for (Integer newEnv : newEnvs) {
                if (newEnv.intValue() == oldEnv.intValue()) {
                    foundInNew = true;
                    break;
                }
            }
            if (!foundInNew) {
                System.out.println(oldEnv);
            }
        }
    }

    @Test
    public void test3() {
        ArrayList<Integer> oldEnvs = Lists.newArrayList(1, 2, 3, 5);
        ArrayList<Integer> newEnvs = Lists.newArrayList(2, 3, 4);
        List<Integer> wasteEnvs = Lists.newArrayList(oldEnvs);
        wasteEnvs.removeAll(newEnvs);
        System.out.println(oldEnvs);
        System.out.println(newEnvs);
        System.out.println(wasteEnvs);
    }

    @Test
    public void test4() {
        ArrayList<Integer> oldEnvs = Lists.newArrayList(1, 2, 3, 5);
        ArrayList<Integer> newEnvs = Lists.newArrayList(2, 3, 4);
        for (Integer newEnv : newEnvs) {
            boolean foundInOld = false;
            // 与旧的比较，如果name同，value不同，则更新
            for (Integer oldEnv : oldEnvs) {
                if (newEnv.intValue() == oldEnv.intValue()) {
                    foundInOld = true;
//                    if (!newEnv.getValue().equals(oldEnv.getValue())) {
//                        insertUpdateEnvHistory(applicationId, userId, parent, newEnv, oldEnv);
//                    }
                    System.out.println("1:" + newEnv);
                    break;
                }
            }
            // 如果该newEnv是全新的，则
            if (!foundInOld) {
//                insertAddEnvHistory(applicationId, userId, parent, newEnv);
                System.out.println("2:" + newEnv);
            }
        }
    }

    @Test
    public void test5() {
        ArrayList<Integer> oldEnvs = Lists.newArrayList(1, 2, 3, 5);
        ArrayList<Integer> newEnvs = Lists.newArrayList(2, 3, 4);
        // 交集
        List<Integer> intersection = Lists.newArrayList(newEnvs);
        intersection.retainAll(oldEnvs);
        System.out.println(oldEnvs);
        System.out.println(newEnvs);
        System.out.println("1:" + intersection);
        // 差集（new-old）
        List<Integer> diff = Lists.newArrayList(newEnvs);
        diff.removeAll(oldEnvs);
        System.out.println(oldEnvs);
        System.out.println(newEnvs);
        System.out.println("2:" + diff);
    }

    private void fun() {
        if (1 == 1) {
            return;
        }
    }

    @Test
    public void test6() throws Exception {
        Set set1 = new HashSet(Lists.newArrayList(1, 2, 3));
        Set set2 = new HashSet(Lists.newArrayList(2, 3, 4));
        Sets.symmetricDifference(set1, set2).stream().forEach((s) -> System.out.println(s));

        String jarName = "aliyun-log-logback-appender-0.1.9.jar";
        System.out.println(jarName.substring(0, jarName.lastIndexOf("-")));

        String beforeJarName = "jackson-core:2.8.8.1.jar";
        String afterJarName = "jackson-core:2.8.8.jar";
        String beforeVersion = beforeJarName.substring(beforeJarName.indexOf(":") + 1, beforeJarName.length() - 4);
        String afterVersion = afterJarName.substring(afterJarName.indexOf(":") + 1, afterJarName.length() - 4);
        if (compareVersion(beforeVersion, afterVersion) > 0) {
            System.out.println("降级");
        } else {
            System.out.println("升级");
        }
    }

    @Test
    public void dependenceList() throws Exception {
        List<String> modules = Lists.newArrayList(
                "zcm-agent",
                "zcm-application",
                "zcm-cicd",
                "zcm-cmdb",
                "zcm-collect",
                "zcm-core",
                "zcm-gateway",
                "zcm-image",
                "zcm-log",
                "zcm-monitor",
                "zcm-portal",
                "zcm-resource",
                "zcm-service",
                "zcm-ssc",
                "sqlplus-script-runner",
                "sqlparser",
                "zcm-tool");

        String moduleName = modules.get(16);

        List<String> list1 = Files.readLines(new File("/Users/kongyunhui/Desktop/zcm-parent/kp/" + moduleName + "-pom-before.txt"), Charset.defaultCharset());
        List<String> list2 = Files.readLines(new File("/Users/kongyunhui/Desktop/zcm-parent/kp/" + moduleName + "-pom-after.txt"), Charset.defaultCharset());

        List<String> jarList1 = list1.stream().map((s) -> {
            String[] split = s.split(":");
            return split[1] + ":" + split[3] + "." + split[2];
        }).sorted().collect(Collectors.toList());
        List<String> jarList2 = list2.stream().map((s) -> {
            String[] split = s.split(":");
            return split[1] + ":" + split[3] + "." + split[2];
        }).sorted().collect(Collectors.toList());

        // 相同的
        System.out.println("######### 相同的 ##########");
        for (String beforeJarName : jarList1) {
            for (String afterJarName : jarList2) {
                if (beforeJarName.equals(afterJarName)) {
                    System.out.println(beforeJarName);
                    break;
                }
            }
        }

        // 包相同，版本号不同的
        List<String> beforeList = new ArrayList<>();
        List<String> afterList = new ArrayList<>();
        List<String> versionChangeDescList = new ArrayList<>();
        for (String beforeJarName : jarList1) {
            for (String afterJarName : jarList2) {
                String beforeArtifactId = beforeJarName.substring(0, beforeJarName.indexOf(":"));
                String afterArtifactId = afterJarName.substring(0, afterJarName.indexOf(":"));
                if (!beforeJarName.equals(afterJarName) && beforeArtifactId.equals(afterArtifactId)) {
                    beforeList.add(beforeJarName);
                    afterList.add(afterJarName);
                    String beforeVersion = beforeJarName.substring(beforeJarName.indexOf(":") + 1, beforeJarName.length() - 4);
                    String afterVersion = afterJarName.substring(afterJarName.indexOf(":") + 1, afterJarName.length() - 4);
                    if (compareVersion(beforeVersion, afterVersion) > 0) {
                        versionChangeDescList.add("降级");
                    } else {
                        versionChangeDescList.add("升级");
                    }
                    break;
                }
            }
        }
        if (CollectionUtils.isNotEmpty(beforeList)) {
            System.out.println("###########  版本变更前的 ##############");
            beforeList.stream().forEach((s) -> System.out.println(s));
        }

        if (CollectionUtils.isNotEmpty(afterList)) {
            System.out.println("###########  版本变更后的 ##############");
            afterList.stream().forEach((s) -> System.out.println(s));
        }

        if (CollectionUtils.isNotEmpty(versionChangeDescList)) {
            System.out.println("###########  版本变更情况 ##############");
            versionChangeDescList.stream().forEach((s) -> System.out.println(s));
        }

        // 删除的
        System.out.println("############### 删除的 ##################");
        for (String beforeJarName : jarList1) {
            boolean isDel = true;
            for (String afterJarName : jarList2) {
                String beforeArtifactId = beforeJarName.substring(0, beforeJarName.indexOf(":"));
                String afterArtifactId = afterJarName.substring(0, afterJarName.indexOf(":"));
                if (beforeArtifactId.equals(afterArtifactId)) {
                    isDel = false;
                    break;
                }
            }
            if (isDel) {
                System.out.println(beforeJarName);
            }
        }

        // 新增的
        System.out.println("############### 新增的 ##################");
        for (String afterJarName : jarList2) {
            boolean isNew = true;
            for (String beforeJarName : jarList1) {
                String afterArtifactId = afterJarName.substring(0, afterJarName.lastIndexOf(":"));
                String beforeArtifactId = beforeJarName.substring(0, beforeJarName.lastIndexOf(":"));
                if (afterArtifactId.equals(beforeArtifactId)) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                System.out.println(afterJarName);
            }
        }
    }

    @Test
    public void commonDependenceList() throws Exception {
        List<String> modules = Lists.newArrayList(
                "zcm-agent",
                "zcm-application",
                "zcm-cicd",
                "zcm-cmdb",
                "zcm-collect",
                "zcm-core",
                "zcm-gateway",
                "zcm-image",
                "zcm-log",
                "zcm-monitor",
                "zcm-portal",
                "zcm-resource",
                "zcm-service",
                "zcm-ssc",
                "sqlplus-script-runner",
                "sqlparser",
                "zcm-tool");

        List<List<String>> allJarList = new ArrayList<>();
        for (String moduleName : modules) {
            List<String> list = Files.readLines(new File("/Users/kongyunhui/Desktop/zcm-parent/kp/" + moduleName + "-pom-after.txt"), Charset.defaultCharset());
            List<String> jarList = list.stream().map((s) -> {
                String[] split = s.split(":");
                return split[1] + ":" + split[3] + "." + split[2];
            }).sorted().collect(Collectors.toList());
            allJarList.add(jarList);
        }
        List<String> sameElem = findSameElem(allJarList);
        sameElem.stream().forEach((s) -> System.out.println(s));
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    /**
     * 获取多个list中的相同元素
     *
     * @param lists
     * @param <E>
     * @return
     */
    public static <E> List<E> findSameElem(List<List<E>> lists) {
        if (lists.size() == 0) {
            return Collections.emptyList();
        }
        // 从第一个list开始，依次求交集，最后得出的集合必然是每个list都有的元素
        List<E> tmpList = new ArrayList<>(lists.get(0));
        for (int i = 1; i < lists.size(); i++) {
            List<E> aList = lists.get(i);
            tmpList.retainAll(aList);
        }
        return tmpList;
    }

    @Test
    public void test11() throws Exception {
        System.out.println(TimeZone.getDefault().getID());
        Date date = new Date();
        System.out.println("时间戳1:" + date.getTime());
        System.out.println("时间戳2:" + System.currentTimeMillis());
        System.out.println("UTC时间：" + dateToUtcStr(new Date()));
        String bjDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("北京时间：" + bjDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bjDate));
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 8);
        System.out.println("UTC时间: " + calendar.getTime());


        DateTime dateTime = new DateTime(2018, 7, 11, 16, 22, 56);
        System.out.println(dateTime);
        DateTime dateTimeUtc = dateTime.toDateTime(DateTimeZone.UTC);
        System.out.println(dateTimeUtc);
    }

    public static String dateToUtcStr(Date date) {
        return Instant.ofEpochMilli(date.getTime()).toString();
    }

    @Test
    public void testStr() throws Exception {
        String appName = "中国";
        System.out.println(appName.hashCode());

        byte[] bs = appName.getBytes();
        String s = new String(bs, "US-ASCII");
        System.out.println(s);

        System.out.println(SumStrAscii(new StringBuffer("中国dasdasdbjkas-!@#$%12232").reverse().toString()));
//        System.out.println(SumStrAscii("国中"));
//        System.out.println(SumStrAscii("ab"));
//        System.out.println(SumStrAscii("ba"));


        String ss = "153173532801512345678";
        if (ss.length() > 18) {
            System.out.println(Long.parseLong(ss.substring(0, 18)));
        } else {
            System.out.println(Long.parseLong(ss));
        }
    }

    @Test
    public void testJsonPath() throws Exception {
        String json = "{\n" +
                "    \"book\": \n" +
                "    [\n" +
                "        {\n" +
                "            \"title\": \"Beginning JSON\",\n" +
                "            \"author\": \"Ben Smith\",\n" +
                "            \"price\": 49.99\n" +
                "        },\n" +
                " \n" +
                "        {\n" +
                "            \"title\": \"JSON at Work\",\n" +
                "            \"author\": \"Tom Marrs\",\n" +
                "            \"price\": 29.99\n" +
                "        },\n" +
                " \n" +
                "        {\n" +
                "            \"title\": \"Learn JSON in a DAY\",\n" +
                "            \"author\": \"Acodemy\",\n" +
                "            \"price\": 8.99\n" +
                "        },\n" +
                " \n" +
                "        {\n" +
                "            \"title\": \"JSON: Questions and Answers\",\n" +
                "            \"author\": \"George Duckett\",\n" +
                "            \"price\": 6.00\n" +
                "        }\n" +
                "    ],\n" +
                " \n" +
                "    \"price range\": \n" +
                "    {\n" +
                "        \"cheap\": 10.00,\n" +
                "        \"medium\": 20.00\n" +
                "    }\n" +
                "}";

        List<Map<String, Object>> books = JsonPathUtil.find(json, "$.book");
        System.out.println(books);
        Map<String, Object> book = JsonPathUtil.findOne(json, "$.book[0]");
        System.out.println(book);
        List<String> titles = JsonPathUtil.find(json, "$.book[*].title");
        System.out.println(titles);
    }

    private static long SumStrAscii(String str) {
        byte[] bytestr = str.getBytes();
        long sign = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytestr.length; i++) {
            sb.append(bytestr[i]);
        }
        System.out.println("->sb:" + sb);
        String s = sb.toString().replace("-", "").substring(0, 18);
        System.out.println("->s:" + s);
        sign = Long.parseLong(s);
        return 0;
    }

    @Test
    public void testMatchStr() {
        String ruleId = "oss@db_gray";
        boolean flag = ruleId.endsWith("_gray");
        System.out.println(flag);
    }

    @Test
    public void javaBasic() {
        String a = "a" + "1";
        String b = "a1";
        System.out.println(a == b);

        Integer i1 = new Integer(1000);
        Integer i2 = new Integer(1000);
        System.out.println(i1 == i2);

        Integer i3 = 127;
        Integer i4 = 127;
        System.out.println(i3 == i4);
    }

    @Test
    public void fun10() {
        String str = "1234";
        char[] chars = str.toCharArray();
        int sum = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            char ch = chars[i];
            int v = (int) ch - (int) '0';
            sum += v * Math.pow(10, (chars.length - 1 - i));
        }
        System.out.println(sum);

        System.out.println(Integer.valueOf(str));
    }

    @Test
    public void fun11() {
        double sum = 0.0;
        for (int i = 1; i <= 20; i++) {
            double v = 1 / (2.0 * i);
            sum += v;
        }
        System.out.println(sum);
    }

    @Test
    public void fun12() {
        List<Integer> v1 = Lists.newArrayList(1, 3, -5);
        List<Integer> v2 = Lists.newArrayList(-2, 4, 1);
        Map<String, Integer> map = new HashMap();
        for (int i = 0; i < v1.size(); i++) {
            for (int j = 0; j < v2.size(); j++) {
                map.put(i + "" + j, v1.get(i) * v2.get(j));
            }
        }
        System.out.println(map);
        ArrayList values = new ArrayList(map.values());
        Set<String> set = map.keySet();
        List<String> keys = new ArrayList<>(set);
        int min = 99999999;
        String km1 = null, km2 = null, km3 = null;
        for (int i = 0; i < keys.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                for (int k = 0; k < keys.size(); k++) {
                    String key1 = keys.get(i);
                    String key2 = keys.get(j);
                    String key3 = keys.get(k);

                    ArrayList<String> xs = Lists.newArrayList(key1.toCharArray()[0] + "", key2.toCharArray()[0] + "", key3.toCharArray()[0] + "");
                    ArrayList<String> ys = Lists.newArrayList(key1.toCharArray()[1] + "", key2.toCharArray()[1] + "", key3.toCharArray()[1] + "");
                    Collections.sort(xs);
                    Collections.sort(ys);
                    StringBuffer sb = new StringBuffer();
                    for (String x : xs) {
                        sb.append(x);
                    }
                    for (String y : ys) {
                        sb.append(y);
                    }
                    //满足xy不重复使用 && 筛选最小值
                    if ((sb.toString().equals("012012")) && (map.get(key1) + map.get(key2) + map.get(key3) < min)) {
                        min = map.get(key1) + map.get(key2) + map.get(key3);
                        km1 = key1;
                        km2 = key2;
                        km3 = key3;
                    }
                }
            }
        }
        System.out.println(km1 + "+" + km2 + "+" + km3 + "=" + min);
    }

    @Test
    public void fun13() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(0, 0);
        linkedList.add(1, 1);
        linkedList.add(2, 2);
        System.out.println(linkedList);
        linkedList.add(1, 11);
        System.out.println(linkedList);
        linkedList.set(1, "aa");
        System.out.println(linkedList);
        Object o = linkedList.get(1);
        System.out.println(o instanceof String);
        System.out.println(linkedList.getFirst());
        System.out.println(linkedList.getLast());
    }

    @Test
    public void fun14() {
        listAllNoRepeate(Lists.newArrayList("1", "2", "2"), "", new HashSet<>());
    }

    /**
     * 通用思路（递归）：
     * 每次从候选集candicate中remove一个数，并加入前缀prefix，打印prefix；
     * 再递归从新的candicate中remove下一个数，并加入prefix
     * <p>
     * 重复控制：
     * HashSet用于控制不重复
     * <p>
     * 组合：打印所有prefix
     * 排列：当候选集被抽空后，才打印prefix
     *
     * @param candicate
     * @param prefix
     */
    private void listAllNoRepeate(List<String> candicate, String prefix, HashSet<String> res) {
//        if (prefix.length() != 0 && !res.contains(prefix)) {
//            System.out.println(prefix);//结果长度不为0,则打印输出该组合
//            res.add(prefix);
//        }

        if (candicate.size() == 0) {
            System.out.println(prefix);
        }

        for (int i = 0; i < candicate.size(); i++) {
            //将list转换成linklist链表，方便操作
            List<String> tempList = new LinkedList<>(candicate);
            //templist减少一个数字，并暂存templist中去除的数字
            String tempString = tempList.remove(i);
            //递归
            listAllNoRepeate(tempList, prefix + tempString, res);
        }
    }

    /**
     * 有2个向量v1(x1,x2,...)和v2(y1,y2,...)，向量中成员可以任意排列，求v1*v2=(x1y1+x2y2+...)的最小值
     */
    @Test
    public void fun15() {
        List<Integer> v1 = Lists.newArrayList(1, 3, -5);
        List<Integer> v2 = Lists.newArrayList(-2, 4, 1);
        //排列
        List<List<Integer>> res1 = new ArrayList<>();
        List<List<Integer>> res2 = new ArrayList<>();
        randomArrange(res1, v1, "");
        randomArrange(res2, v2, "");
        System.out.println(res1);
        System.out.println(res2);
        //笛卡尔乘积式组合，求向量和
        int min = Integer.MAX_VALUE;
        for(int i=0; i<res1.size(); i++){
            for(int j=0; j<res2.size(); j++){
                List<Integer> vx = res1.get(i);
                List<Integer> vy = res2.get(j);
                int sum=0;
                for(int k=0; k<vx.size(); k++){
                    sum += vx.get(k) * vy.get(k);
                }
                if(sum < min) {
                    min = sum;
                }
            }
        }
        System.out.println(min);
    }

    private void randomArrange(List<List<Integer>> res, List<Integer> candicate, String prefix) {
        if (candicate.size() == 0) {
            String[] split = prefix.split("\\|");
            res.add(Lists.newArrayList(Integer.valueOf(split[0]), Integer.valueOf(split[1]), Integer.valueOf(split[2])));
        }
        for (int i = 0; i < candicate.size(); i++) {
            List<Integer> tempList = new LinkedList<>(candicate);
            Integer tempString = tempList.remove(i);
            randomArrange(res, tempList, prefix+tempString+"|");
        }
    }

    /**
     * 给定一个由小写英文字母构成的字符串S，求不含重复字母的子串的最大长度
     */
    @Test
    public void fun16(){
        String s = "abbcdde";
        int max = Integer.MIN_VALUE;
        for(int i=0; i<s.length(); i++){
            for(int j=i+1; j<s.length()+1; j++){
                String substring = s.substring(i, j);
                if(noRepeatChar(substring) && substring.length()>max){
                    max = substring.length();
                }
            }
        }
        System.out.println(max);
    }

    private boolean noRepeatChar(String string){
        String[] split = string.split("");
        List<String> list = new ArrayList<>();
        for(int i=0; i<split.length; i++){
            if(list.contains(split[i])){
                return false;
            }
            list.add(split[i]);
        }
        return true;
    }

    /**
     * [-3,1,2,-4,7,1]
     */
    @Test
    public void fun17(){
        ArrayList<Integer> list = Lists.newArrayList(-3, 1, 2, -4, 7, 1);
        Map<String, Integer> map = new HashMap<>();
        //每趟叠加决出最大值和下标
        for(int i=0; i<list.size();i++){
            int temp = list.get(i);
            String key = i+","+i;
            int maxVal = temp;
            for(int j=i+1; j< list.size(); j++){
                temp += list.get(j);
                if(temp > maxVal){
                    key = i+","+j;
                    maxVal = temp;
                }
            }
            map.put(key, maxVal);
        }
        //遍历map，找出maxValue,并输出key
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
        String key="";
        int maxValue = Integer.MIN_VALUE;
        while(iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            String k = next.getKey();
            Integer v = next.getValue();
            if(v > maxValue){
                key = k;
                maxValue = v;
            }
        }
        System.out.println(key+ ":" + maxValue);
    }

    @Test
    public void fun18(){
        List<String> list = Lists.newArrayList("abc", "abbcd", "abcd");
        //获取字符串子集
        List<List<String>> allSubList = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            List<String> sub = new ArrayList<>();
            String str = list.get(i);
            for(int j=0; j<str.length();j++){
                for(int k=j;k<str.length(); k++){
                    sub.add(str.substring(j, k+1));
                }
            }
            allSubList.add(sub);
        }
        //获取交集
        List<String> arr = allSubList.get(0);
        int index = allSubList.size()-1;
        while(index>0){
            Sets.SetView<String> intersection = Sets.intersection(new HashSet<>(arr), new HashSet<>(allSubList.get(index)));
            arr = intersection.immutableCopy().asList();
            index --;
        }
        //获取最大长度的交集元素
        int max = Integer.MIN_VALUE;
        for(int i=0; i<arr.size(); i++){
            if(arr.get(i).length()>max){
                max = i;
            }
        }
        System.out.println(arr.get(max));
    }

    @Test
    public void fun19(){
        List<List<String>> allList = Lists.newArrayList(Lists.newArrayList("a", "ab"), Lists.newArrayList("ab", "c"), Lists.newArrayList("ab", "c", "a"));
        List<String> arr = allList.get(0);
        for (int i=1; i<allList.size(); i++){
            String[] tempArr = intersection(arr.toArray(new String[arr.size()]), allList.get(i).toArray(new String[allList.get(i).size()]));
            arr = Arrays.asList(tempArr);
        }
        System.out.println(arr);
    }

    private String[] intersection(String[] arr1, String[] arr2){
        String[] arr = new String[arr1.length>arr2.length?arr2.length:arr1.length];
        int index = 0;
        for(int i=0; i<arr1.length; i++){
            for(int j=0; j<arr2.length; j++){
                if(arr1[i].equals(arr2[j])){
                    //交集入库
                    arr[index] = arr1[i];
                    index++;
                    //比较过的删除掉（优化，注意：这就是使用普通数组的原因，List是不允许for的时候remove的）
                    arr2[j] = arr2[arr2.length-1];
                    Arrays.copyOf(arr2, arr2.length-1);
                    //结束本地循环
                    break;
                }
            }
        }
        return Arrays.copyOf(arr, index); // 正式返回的数组下标可能比length1,length2小
    }

    @Test
    public void fun20(){
        int rows = 10;

        for(int i =0;i<rows;i++) {
            int number = 1;
            //打印空格字符串
            System.out.format("%"+(rows-i)*2+"s","");
            //打印i+1个数
            for(int j=0;j<=i;j++) {
                System.out.format("%4d",number);
                number = number * (i - j) / (j + 1);
            }
            System.out.println();
        }
    }

    @Test
    public void fun21(){
        System.out.println(tryTest());
    }

    private int tryTest(){
        try{
            return 1/0;
        }catch (Exception e){
            return -1;
        }finally {
            return 0;
        }
    }
}
