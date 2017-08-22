package com.kyh.java8;

import com.drew.lang.annotations.NotNull;
import com.google.common.collect.Lists;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by kongyunhui on 2017/7/3.
 */
public class Demo {
    public static void main(String[] args) {
        // 1、典型的Lambda表达式
        new Thread(() -> {
            System.out.println("这里是Lambda实现方式");
        }).start();
        new Thread(() -> "s".equals("s")).start();

        // 2、内部迭代，由jdk 库来控制循环，并行，乱序或者懒加载方式。
        ArrayList<String> list = Lists.newArrayList("Java", "Scala", "C++", "Java", "Haskell", "Lisp", "JavaScript");
        list.forEach(s -> System.out.println(s));
        System.out.println("###");
        list.forEach(System.out::println); // 方法引用

        // 3、Stream API （函数式编程）
        List<SearchResult> r = list
                .stream().parallel()            // Stream.parallel() 表示并行流，不加就是顺序流
                .filter(s -> s.startsWith("J")) // 过滤
                .map(s -> new SearchResult(s))  // 转换对象，Function<T,R>
                .distinct()                     // 去重
                .collect(Collectors.toList());
        System.out.println("New language list:" + r);

        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double bill = costBeforeTax
                .stream()
                .map((cost) -> cost + 0.12 * cost)
                .reduce((sum, cost) -> sum + cost) // 聚合
                .get();
        System.out.println("Total : " + bill);

        List<Order> orders = initOrders();
        orders.stream()
                .flatMap(o -> o.getProducts().stream())
                .forEach(System.out::println);


        IntSummaryStatistics intSummaryStatistics = costBeforeTax
                .stream()
                .mapToInt((x) -> x)
                .summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getSum());

        // 4、类型注解
//        @NonNull Object obj = null;

        // 5、日期
        System.out.println(Instant.now());
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.of(2017, 7, 6, 14, 25, 30).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        System.out.println(MonthDay.of(7, 20));

        /**
         *  性能对比
         */
        ArrayList<Integer> integers = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            integers.add(new Random().nextInt(100000));
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                long start1 = System.nanoTime();
                int max1 = 0;
                for (Integer i : integers) {
                    max1 = Integer.max(max1, i);
                }
                System.out.println("max1 is: " + max1);
                System.out.println("process1 time is: " + (System.nanoTime() - start1));
            }
        }).start();

        new Thread(() -> {
            long start2 = System.nanoTime();
            int max2 = 0;
            max2 = integers.parallelStream().reduce(Integer::max).get();
            System.out.println("max2 is: " + max2);
            System.out.println("process2 time is: " + (System.nanoTime() - start2));
        }).start();
//
        new Thread(() -> {
            long start3 = System.nanoTime();
            int max3 = 0;
            max3 = integers.parallelStream().mapToInt((i) -> i).summaryStatistics().getMax();
            System.out.println("max3 is: " + max3);
            System.out.println("process3 time is: " + (System.nanoTime() - start3));
        }).start();

        // 结论：使用传统迭代器和 for-each 循环的 Java 编程风格比 Java 8 中的新方式性能高很多！！！


        // 函数式编程核心原则：不可修改外部变量，特别是并发时！！
        new Thread(() -> {
            long start4 = System.nanoTime();
            final Person p = new Person();
            p.setAge(Integer.MIN_VALUE);
            integers.stream().forEach(i -> p.setAge(Integer.max(p.getAge(), i)));
            System.out.println("max4 is: " + p.getAge());
            System.out.println("process4 time is: " + (System.nanoTime() - start4));
        }).start();

        new Thread(() -> {
            long start5 = System.nanoTime();
            final Person p = new Person();
            p.setAge(Integer.MIN_VALUE);
            integers.parallelStream().forEach(i -> p.setAge(Integer.max(p.getAge(), i)));
            System.out.println("max5 is: " + p.getAge());
            System.out.println("process5 time is: " + (System.nanoTime() - start5));
        }).start();
        // 你会发现：max5经常发生错误！因为它破坏了函数式编程的核心原则！！
        //
        // 原因：这里涉及一个并发计数器问题。即多个线程同时作用于同一块内存单元，写操作有很大机会丢失。
        // 解决：要么不使用并发流（或者使用reduce、summaryStatistics处理），要么保证数据同步（如：锁）


        /**
         * 并发计数器
         */
        ArrayList<Integer> array = Lists.newArrayList(10, 20, 30, 40, 50);
        LongAdder la = new LongAdder();
        array.parallelStream().forEach(i -> la.add(i));
        System.out.println(la.intValue());


        /**
         * 排序(多种写法)
         */
        ArrayList<Person> persons = Lists.newArrayList(new Person("kong", 22), new Person("yun", 24), new Person("hui", 23));
        // 简单排序
        List<Person> collect1 = persons
                .stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .collect(Collectors.toList());
        System.out.println(collect1);

        // 组合排序
        List<Person> collect2 = persons
                .stream()
                .sorted((p1, p2) -> {
                    if (p1.getName().equals(p2.getName())) {
                        return p1.getAge() - p2.getAge();
                    } else {
                        return p1.getName().compareTo(p2.getName());
                    }
                })
                .collect(Collectors.toList());
        System.out.println(collect2);

        // 组合排序 - 方法引用
        List<Person> collect3 = persons
                .stream()
                .sorted(Comparator.comparing(Person::getName).thenComparing(Person::getAge)) // 静态方法
                .collect(Collectors.toList());
        System.out.println(collect3);

        /**
         * Optional
         */
        Optional<String> name = Optional.ofNullable(null);
        name.ifPresent((value) -> System.out.println(name.get()));
        System.out.println(name.orElseGet(() -> "Default Value"));

        /**
         * Random
         *
         * double类型的伪随机数是均匀的分配的，而double类型的高斯伪随机数应该是正态分布的。
         */
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(-1.0, 1.0); // 伪随机数流
        doubleStream.limit(10).forEach(System.out::println);

        System.out.println("######");

        DoubleStream doubleStream1 = Stream.generate(random::nextGaussian).mapToDouble((d) -> d); // 高斯伪随机数流
        doubleStream1.filter(d -> d >= -1.0 && d < 1.0).limit(10).forEach(System.out::println);

        /**
         * 多线程同步（当然，如果只是做并发计数器，建议使用LongAdder）
         */
        Person person = new Person("kong", 22);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> submit1 = executorService.submit(() -> increaseAge4Lock(person));
        Future<?> submit2 = executorService.submit(() -> increaseAge4StampedLock(person));
        executorService.shutdown();

        /**
         * 从集合或数组创建流
         */
        Arrays.stream(new int[]{10, 20, 30, 40}).parallel().max().ifPresent(System.out::println);

        Stream.of(new int[]{10, 20, 30, 40});

    }

    public static void increaseAge4Lock(Person person){
        // 线程增长能够预估
        Lock lock = new ReentrantReadWriteLock().writeLock();
        try{
            lock.lock();
            System.out.println("-i1 read age-->" + person.getAge());
            person.increase();
            System.out.println("-i1 new age-->" + person.getAge());
        } finally {
            lock.unlock();
        }
    }

    public static void increaseAge4StampedLock(Person person){
        // 仅适用于读取执行情况很多，写入很少的情况下（此时我们才能乐观的认为读写并发的几率很小，否则得不偿失）
        // 乐观读锁：直接读取信息，并检查读取后是否遭到写入执行的变更。若被入侵则重新读取变更信息，反之直接使用
        StampedLock sl = new StampedLock();
        long stamp = sl.tryOptimisticRead();
        int localAge = person.getAge(); // 将字段读入本地局部变量①
        if(!sl.validate(stamp)){ // 检查发出乐观读锁后同时是否有其他写锁发生? 如果被入侵，重新获取读锁读取变更信息。
            stamp = sl.readLock();
            try{
                localAge = person.getAge(); // 重新将字段读入本地局部变量②
            } finally {
                sl.unlockRead(stamp);
            }
        }
        System.out.println("-i2 read age-->" + localAge);
        person.setAge(++localAge);
        System.out.println("-i2 new age-->" + person.getAge());
        if(person.getAge()!=24) System.out.println("--->error");
    }

    private static List<Order> initOrders(){
        List<Order> orders = new ArrayList<>();
        for(int i=0; i<3; i++){
            Order order = new Order();
            order.setId(i+"");
            order.setName(i+"");
            for(int j=0; j<3; j++){
                Product product = new Product();
                product.setId(i+""+j);
                product.setName(i+""+j);
                order.getProducts().add(product);
            }
            orders.add(order);
        }
        System.out.println(orders);
        return orders;
    }
}
