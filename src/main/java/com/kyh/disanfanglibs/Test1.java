package com.kyh.disanfanglibs;

import com.google.common.base.*;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import com.google.common.hash.*;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Created by kongyunhui on 16/8/30.
 */
public class Test1 {
    public static Multiset multiset;

    public static void main(String[] args) {
        try {
            // checkNotNull
            Object s = "";
            s = Preconditions.checkNotNull(s); // 这句话通过,才会执行接下来的代码(前置条件)
            System.out.println("--->" + s.toString());

            System.out.println(Objects.equal(null, null));

            int i = Objects.hashCode("1");
            System.out.println(i);

            // 排序
            Person p1 = new Person("002", 2);
            Person p2 = new Person("001", 1);
            List<Person> list = new ArrayList<Person>();
            list.add(p1);
            list.add(p2);
            Collections.sort(list);
            System.out.println(list);
//            Collections.sort(list, new Comparator<Person>() {
//                public int compare(Person o1, Person o2) {
//                    return ComparisonChain.start()
//                            .compare(o1.getName(), o2.getName())
//                            .compare(o1.getAge(), o2.getAge())
//                            .result();
//                }
//            });
//            System.out.println(list);

            // 从实现上说，Ordering实例就是一个特殊的Comparator实例
            // 拥有更多的api,复杂排序建议使用Ordering,简单排序使用Comparable+ComparisonChain
            List<Student> list2 = new ArrayList<Student>();
            list2.add(new Student("002", 2));
            list2.add(new Student("001", 1));
            list2.add(new Student(null, 3));
            Ordering<Student> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Student, Comparable>() {
                public Comparable apply(Student student) {
                    return student.getName();
                }
            });
            Collections.sort(list2, ordering); // 使用ordering规则排序
            System.out.println(list2);
            List<Student> ss1 = ordering.greatestOf(list2.iterator(), 1);// 返回最大的k个元素
            System.out.println(ss1);
            Student ss2 = ordering.min(list2); // 返回最小的元素
            System.out.println(ss2);

            // 不可变集合(安全、高效) 包装好的数据"return ImmutableList.copyOf(list2)"可以得到更好的读取效率
            ImmutableList<Student> immutableList = ImmutableList.copyOf(list2);
            System.out.println(immutableList);
            ImmutableMap<String, ? extends Serializable> immutableMap = ImmutableMap.of("name", "kong", "age", 24);
            System.out.println(immutableMap.get("name"));

            ImmutableList<String> words = ImmutableList.of("h", "e", "l", "l", "o", "w", "o", "r", "l", "d");
            // 新集合 (数据统计上有优势)
            // Multiset
            Multiset<String> multiset = HashMultiset.create();
            multiset.addAll(words);
            System.out.println(multiset);
            System.out.println(multiset.count("o")); // 统计该单词出现的次数
            System.out.println(multiset.size()); // 统计总单词数
            System.out.println(multiset.elementSet()); // 统计不重复单词的集合
            multiset.setCount("o", 0); // 设置某单词出现的次数
            System.out.println(multiset);
            multiset.add("o", 3); // 添加某单词出现的次数
            System.out.println(multiset);
            // SortedMultiset
            SortedMultiset<String> sortedMultiset = TreeMultiset.create(words);
            System.out.println(sortedMultiset);
            // Multimap
            Multimap<String, List<String>> multimap = ArrayListMultimap.create(); // 实现一个键映射到多个值
            multimap.put("list1", words);
            multimap.put("list2", words);
            System.out.println(multimap);
            ArrayList<List<String>> lists = new ArrayList<List<String>>();
            lists.add(words);
            lists.add(words);
            multimap.putAll("list2", lists);
            System.out.println(multimap);
            Collection<List<String>> list1 = multimap.asMap().get("list1");
            System.out.println(list1.iterator().next().get(0));
            // BiMap
            BiMap<String, Integer> biMap = HashBiMap.create(); // K-V双向映射
            biMap.put("kong", 001);
            System.out.println(biMap.get("kong"));
            BiMap<Integer, String> inverse = biMap.inverse();
            System.out.println(inverse.get(001));
            // Table
            Table table = HashBasedTable.create();
            table.put(1, 1, "kong");
            table.put(1, 2, "yun");
            table.put(1, 3, "hui");
            System.out.println(table.rowMap());
            System.out.println(table.columnMap());
            System.out.println(table.get(1, 1));
            // 区间处理RangeSet、RangeMap
            // ...

            // 强大的集合工具类(Lists、Sets、Maps)
            ArrayList<String> strings1 = Lists.newArrayList("1", "2", "3");
            ArrayList<String> strings2 = Lists.newArrayList("3", "2", "1");
            System.out.println(strings1.equals(strings2));
            boolean b = Iterables.elementsEqual(strings1, strings2);
            System.out.println(b);
            List<List<String>> partition = Lists.partition(strings1, 2); // 按指定大小分割
            System.out.println(partition);

            // 统计Strings每个元素的长度作为key(ps.string.length()必须是独一无二的)
            ImmutableMap<Integer, String> stringsByIndex = Maps.uniqueIndex(Lists.newArrayList("k", "kong", "hui"),
                    new Function<String, Integer>() {
                        public Integer apply(String string) {
                            return string.length();
                        }
                    });
            System.out.println(stringsByIndex);
            // 统计String每个元素的长度作为key,相同长度的放一起(ps.length比一定独一无二)
            ImmutableListMultimap<Integer, String> index = Multimaps.index(Lists.newArrayList("kong", "yun", "hui"), new Function<String, Integer>() {
                public Integer apply(String string) {
                    return string.length();
                }
            });
            System.out.println(index);

            List<String> result = Lists.newArrayList();
            PeekingIterator<String> iter = Iterators.peekingIterator(ImmutableList.of("1", "2", "2", "3", "1", "4").iterator());
            while (iter.hasNext()) {
                String current = iter.next();
                while (iter.hasNext() && iter.peek().equals(current)) {
                    //跳过重复的元素
                    iter.next();
                }
                result.add(current);
            }
            System.out.println(result);

            // 连接器 Joiner
            String join = Joiner.on(",")
                    .useForNull("")
                    .join("k", "y", null, "h");
            System.out.println(join);

            String sd = ",a,,b,";
            System.out.println(Arrays.asList(sd.split(","))); // JDK自带的split自动忽略尾部的"逗号"
            Iterable<String> split = Splitter.on(",").split(sd);
            System.out.println(split);

            System.out.println(StringUtils.deleteWhitespace(" k o n g ")); // "kong"
            System.out.println(" k o n g ".replace(" ", ""));

            String max = Collections.max(Lists.<String>newArrayList("1", "k", "zsasa"));
            System.out.println(max);

            // IO
            byte[] bytes = Resources.toByteArray(new URL("http://www.baidu.com/")); // 读取所有数据源
            System.out.println(new String(bytes));

            byte[] bytes1 = Files.toByteArray(new File("readme.txt"));
            System.out.println(new String(bytes1));

            ByteSource byteSource = Resources.asByteSource(new URL("http://www.baidu.com/")); // 创建源
            byte[] buffer = new byte[128];
            byteSource.openStream().read(buffer); // 读取一定数据
            System.out.println(new String(buffer));

            CharSource charSource = Resources.asCharSource(new URL("http://www.baidu.com/"), Charsets.UTF_8);
            String s1 = charSource.openBufferedStream().readLine();
            System.out.println(s1);
            ImmutableList<String> immutableList1 = charSource.readLines();
            System.out.println(immutableList1);

            //Read the lines of a UTF-8 text file
            File file = new File("readme.txt");
            ImmutableList<String> lines = Files.asCharSource(file, Charsets.UTF_8).readLines();
            //Count distinct word occurrences in a file
            Multiset<String> wordOccurrences = HashMultiset.create(
                    Splitter.on(CharMatcher.WHITESPACE)
                            .trimResults()
                            .omitEmptyStrings()
                            .split(Files.asCharSource(file, Charsets.UTF_8).read()));
            System.out.println(wordOccurrences.size());
            //SHA-1 a file
            HashCode hash = Files.asByteSource(file).hash(Hashing.sha1());

            //Copy the data from a URL to a file
            URL url = new URL("http://www.baidu.com/");
            Resources.asByteSource(url).copyTo(Files.asByteSink(file));
            // File read and write
            Files.asByteSource(file).openStream().read();
            Files.asByteSink(file).openStream().write(buffer);

            String nameWithoutExtension = Files.getNameWithoutExtension("readme.txt");
            System.out.println(nameWithoutExtension);

            TreeTraverser<File> fileTreeTraverser = Files.fileTreeTraverser();
            System.out.println(fileTreeTraverser.children(new File("src")));

            // HashFunction的实例Hasher为数据添加散列算法
            // 1. 相同的输入一定产生相同的输出,反之不然!
            // 2. put "kongyunhui" 和 put "kong" .put "yunhui"返回的结果是一样的,因为他们拥有相同的字节序列(因此增加某种形式的分隔符有助于消除散列冲突)
            HashFunction hf = Hashing.md5();
            HashCode hc1 = hf.newHasher()
                    .putString("kongyunhui", Charsets.UTF_8)
                    .hash();
            HashCode hc2 = hf.newHasher()
                    .putString("kongyunhui", Charsets.UTF_8)
                    .hash();
            HashCode hc3 = hf.newHasher()
                    .putString("kong", Charsets.UTF_8)
                    .putString("yunhui", Charsets.UTF_8)
                    .hash();
            System.out.println(hc1);
            System.out.println(hc2);
            System.out.println(hc3);

            // 在简单散列表以外的散列运用中，Object.hashCode几乎总是达不到要求 (除非对象重写equals+hashCode)
            ArrayList<Person> persons = Lists.newArrayList(new Person("kong", 22), new Person("yunhui", 21));
            if (persons.contains(new Person("yunhui", 21))) {
                System.out.println("JDK hashCode is succeed!");
            } else {
                System.out.println("JDK hashCode is failure!");
            }
            // java 判断2个list相等
            ArrayList<Person> persons2 = Lists.newArrayList(new Person("kong", 22), new Person("yunhui", 21));
            System.out.println(persons.containsAll(persons2));

            // 布鲁姆过滤器[BloomFilter] -- 保存多个对象,可以判断对象是否已经添加到过滤器了
            BloomFilter<Person> personBloomFilter = BloomFilter.create(new Funnel<Person>() {
                public void funnel(Person from, PrimitiveSink into) {
                    into
                            .putString(from.getName(), Charsets.UTF_8)
                            .putInt(from.getAge());
                }
            }, 500, 0.01);
            personBloomFilter.put(new Person("kong", 22));
            personBloomFilter.put(new Person("yunhui", 21));
            if(personBloomFilter.mightContain(new Person("yunhui", 21))){
                // 不包含yunhui还运行到这里的概率为1%
                // 在这儿，我们可以在做进一步精确检查的同时触发一些异步加载
                System.out.println("Guava hashCode is succeed!");
            } else {
                System.out.println("Guava hashCode is failing!");
            }

            // Joda-Time
            // 获取当前时间
            DateTime dateTime = new DateTime();
            System.out.println(dateTime.toString("yyyy/MM/dd HH:mm:ss.SSS"));
            // 获取指定时间
            DateTime dateTime2 = new DateTime(2016,7,20,12,30,0,0);
            System.out.println(dateTime2.toString("yyyy/MM/dd HH:mm:ss.SSS"));
            // 获取时间戳
            long timestamp = dateTime2.getMillis();
            System.out.println(timestamp);
            // 下一个还款日期 (30天以后)
            DateTime dateTime1 = new DateTime().plusDays(30);
            System.out.println(dateTime1);
            // 5年后的第2个月的最后一天
            DateTime dateTime3 = new DateTime().plusYears(5) // 5年后
                    .monthOfYear()       // 获得"month"属性
                    .setCopy(2)          // 设置2,此处指2月
                    .dayOfMonth()        // 获得"day"属性
                    .withMaximumValue();// 获得最大值,此处指最后一天
            System.out.println(dateTime3);
            // JDK <==> joda
            Calendar calendar = Calendar.getInstance();
            DateTime dt = new DateTime(calendar).plusDays(30);
            calendar = dt.toCalendar(Locale.CHINA);
            System.out.println(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





