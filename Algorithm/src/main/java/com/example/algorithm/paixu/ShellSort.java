package com.example.algorithm.paixu;

public class ShellSort {
    public static void shellSort(int[] arr){
        if(arr == null){
            return;
        }
        int len = arr.length;
        //初始的增量为原来的长度的一半
        int k = len / 2;
        while (k > 0){
            //类似于插入排序
            for(int i = k;i < len;i++){
                int j = k;
                int target = arr[j];
                while (j > k && target < arr[j-k]){
                    arr[j] = arr[j - k];
                    j -= k;
                }
                arr[j] = target;
            }
            k /= 2;
        }
    }
}
