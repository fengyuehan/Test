package com.example.algorithm.paixu;

public class InsertSort {
    public static void insetSort(int[] arr){
        if(arr == null){
            return;
        }
        int len = arr.length;
        //这里i取1，是因为默认第一个数的位置是正确的
        for(int i = 1;i < len;i++){
            int j = i;
            int target = arr[j];
            while (j > 0 && target < arr[j -1]){
                arr[j] = arr[j -1];
                j--;
            }
            arr[j] = target;
        }
    }
}
