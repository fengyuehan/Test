package com.example.algorithm.niukewang;

public class MinNumberInRotateArray {
    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非减排序的数组的一个旋转，
     * 输出旋转数组的最小元素。 例如数组 { 3, 4, 5, 1, 2 } 为 { 1, 2, 3, 4, 5 } 的一个旋转，该数组的最小值为 1。
     * NOTE：给出的所有元素都大于 0，若数组大小为 0，请返回 0。
     * @param arr
     * @return
     */
    public static int minNumberInRotateArray(int[] arr){
        if (arr.length == 0){
            return 0;
        }
        int low = 0;
        int high = arr.length-1;
        int mid = 0;
        while (arr[low] > arr[high]){
            mid = (low + high)/2;
            if (arr[mid] >= arr[low]){//当中间的值比左边的值大，说明较小的值在右面，所以令low = mid
                low = mid;
            }else if (arr[mid] <= arr[high]){//当中间的值比右边的小，说明较小的值在左边，令high = mid
                high = mid;
            }
            if (high - low == 1){
                mid = high;
                break;
            }
        }
        return arr[mid];
    }
}
