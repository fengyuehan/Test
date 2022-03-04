package com.example.algorithm.paixu;

public class SelectSort {
    public static void selectSort(int[] arr){
        if (arr == null){
            return;
        }
        int len = arr.length;
        for (int i = 0; i < len -1;i++){
            int minIndex = i;
            //每次循环结束之后都会把最小的放在最前面，所以就不需要进行比较了。
            for (int j = i+1;j < len; j++){
                if (arr[minIndex] > arr[j]){
                    minIndex = j;
                }
            }
            if (minIndex != i){
                Swap.swap(arr,i,minIndex);
            }
        }
    }
}
