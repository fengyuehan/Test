package com.example.algorithm.paixu;

public class BinarySearch {
    public static int binarySearch(int[] arr,int value){
        int low = 0;
        int high = arr.length -1;
        while (low < high){
            int middle = (low + high)/2;
            if(value > arr[middle]){
                low = middle + 1;
            }else if (value < arr[middle]){
                high = middle -1;
            }else {
                return middle;
            }
        }
        return -1;
    }
}
