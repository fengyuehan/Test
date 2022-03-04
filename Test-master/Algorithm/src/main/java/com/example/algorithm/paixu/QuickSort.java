package com.example.algorithm.paixu;

public class QuickSort {

    public void quickSort(int[] arr,int i,int j){
        i = 0;
        j = arr.length;
        int temp = qSort(arr,i,j);
        quickSort(arr,i,temp-1);
        quickSort(arr,temp + 1,j);
    }

    private static int qSort(int[] arr,int left,int right){
        int temp = arr[left];
        while (left < right){
            while (left < right && temp <= arr[right]){
                right --;
            }
            while (left < right){
                arr[left++] = arr[right];
            }
            while (left < right && arr[left] <= temp){
                left++;
            }
            while (left < right){
                arr[right--] = arr[left];
            }
        }
        arr[left] = temp;
        return left;
    }
}
