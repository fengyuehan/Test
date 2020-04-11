package com.example.algorithm.niukewang;

import com.example.algorithm.paixu.Swap;

public class ReorderOddEven {
    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
     * 使得所有奇数位于数组的前半部分，所有偶数位予数组的后半部分。
     * @param arr
     */
    public void reorderOddEven(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        //从左到右记录偶数的位置
        int start = 0;
        //从右到左记录奇数的位置
        int end = arr.length -1;
        while (start < end){
            while (start < end && arr[start] % 2 != 0 ){
                start++;
            }
            while (start < end && arr[end] % 2 == 0){
                end--;
            }
            //找到偶数和奇数的位置，进行交换
            Swap.swap(arr,start,end);
            /*int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;*/
        }
    }
}
