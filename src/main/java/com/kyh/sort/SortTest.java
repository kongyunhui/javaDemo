package com.kyh.sort;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author kongyunhui on 2018/8/20.
 */
public class SortTest {
    private List<Integer> list = Lists.newArrayList(45, 38, 66, 90, 88, 10, 25);

    /**
     * 插入排序：设置岗哨，前面有序表中的元素依次与岗哨比较，比岗哨大的元素后移，最后插入岗哨
     * <p>
     * 效率：O(n^2)
     * <p>
     * 备注：相等元素的前后顺序没有改变，从原无序序列出去的顺序就是排好序后的顺序，所以插入排序是稳定的。
     */
    @Test
    public void fun1() {
        int i, j;
        //从第二个记录起进行插入
        for (i = 1; i < list.size(); i++) {
            //第i个记录复制为岗哨
            int r0 = list.get(i);
            j = i - 1;
            //有序表中的元素依次与岗哨比较，比岗哨大的元素后移
            while (j >= 0 && r0 < list.get(j)) {
                list.set(j + 1, list.get(j));
                j--;
            }
            //将岗哨插入到有序序列中
            list.set(j + 1, r0);
        }
        System.out.println(list);
    }

    /**
     * 交换排序 - 冒泡排序
     * <p>
     * 改进：如果某一趟没有发生交换，则说明已经排好了，直接退出排序
     */
    @Test
    public void fun2() {
        for (int i = 0; i < list.size() - 1; i++) {
            boolean isExchange = false;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    int temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                    isExchange = true;
                }
            }
            if (!isExchange) {
                break;
            }
        }
        System.out.println(list);
    }

    /**
     * 交换排序 - 快速排序：是目前基于比较的内部排序中被认为是最好的方法，当待排序的关键字是随机分布时，快速排序的平均时间最短
     *
     * 核心：从表的两端交替地向中间扫描，一趟排序会获取一个基准值，将表一分为二，然后再对子表递归快速排序
     */
    @Test
    public void fun3() {
        quickSort(list, 0, list.size()-1);
        System.out.println(list);
    }

    private int partition(List<Integer> list, int low, int high) {
        //基准元素
        int privotKey = list.get(low);
        while (low < high) {
            //从表的两端交替地向中间扫描
            while ((low < high) && list.get(high) >= privotKey) {high--;}
            list.set(low, list.get(high)); //自尾端进行比较，将比基准值小的元素移到低端
            while ((low < high) && list.get(low) <= privotKey) {low++;}
            list.set(high, list.get(low)); //自首端进行比较，将比基准值大的元素移到高端
        }
        list.set(low, privotKey);//第一趟排序结束，将基准值移到其最终位置
        return low;
    }

    private void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            //将表一分为二
            int privotLoc = partition(list, low, high);
            //递归对低子表递归排序
            quickSort(list, low, privotLoc - 1);
            //递归对高子表递归排序
            quickSort(list, privotLoc + 1, high);
        }
    }

    @Test
    public void fun4(){
        Collections.sort(list);
        System.out.println(biSearch(list, list.size(), 90));
    }

    /**
     * 二分查找：给定一个有序的数组，查找value是否在数组中，不存在返回-1
     *
     * @param list
     * @param n
     * @param key
     * @return
     */
    private int biSearch(List<Integer> list, int n, int key) {
        int low = 0;
        int high = n - 1;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (list.get(mid) > key) {
                high = mid - 1;
            } else if (list.get(mid) < key) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 简单选择排序：在未排序序列中找到最小元素，存放到排序序列的起始位置(与本地排序起始位置元素交换)
     */
    @Test
    public void fun5(){
        for(int i=0; i<list.size()-1; i++){
            int minIndex = i;
            for(int j=i+1; j<list.size(); j++){
                if(list.get(j)<list.get(minIndex)){
                    minIndex = j;
                }
            }
            Integer temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
        System.out.println(list);
    }
}
