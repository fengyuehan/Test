package com.example.algorithm.paixu;

public class BubbleSort {
    public static void bubbleSort(int[] arr){
        for (int i = 0;i < arr.length-1;i++){
            //每次把最大的数放在最后，下一次就可以少比较一次，所以需要减i
            for (int j = 0; j < arr.length -i -1;j++){
                if (arr[j] > arr[j+1]){
                    Swap.swap(arr,j,j+1);
                }
            }
        }
    }
}
