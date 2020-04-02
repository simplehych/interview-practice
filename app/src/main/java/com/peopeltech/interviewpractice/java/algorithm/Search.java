package com.peopeltech.interviewpractice.java.algorithm;

/**
 * 查找算法
 *
 * @author hych
 * @since 2020/4/2 21:44
 */
public class Search {

    public static void main(String[] args) {

    }


    /**
     * 顺序查找
     * 复杂度 O(n)
     *
     * @param searchKey 目标查找值
     * @param array     目标数组
     * @return 目标查找值对应数组下标
     */
    public static int orderSearch(int searchKey, int[] array) {
        if (array == null || array.length < 1) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == searchKey) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 二分查找/折半查找
     * 复杂度 O(logN)
     * 要求：1. 顺序存储结构；2. 有序排列；
     * 方法：1. while循环；2. 递归；
     *
     * @param searchKey 目标查找值
     * @param array     目标数组
     * @return 目标查找值对应数组下标
     */
    public static int binarySearch(int searchKey, int[] array) {
        if (array == null || array.length < 1) {
            return -1;
        }

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (high + low) / 2;

            if (searchKey == array[mid]) {
                return mid;
            } else if (searchKey < array[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

}
