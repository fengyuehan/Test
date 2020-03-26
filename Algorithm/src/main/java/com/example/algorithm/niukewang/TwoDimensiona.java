package com.example.algorithm.niukewang;

public class TwoDimensiona {
    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public static boolean find(int target,int[][] arr){
        int rows = arr.length;
        int lines = arr[0].length;
        int i = 0;
        while(i < lines && rows >= 0 && lines >= 0){
            if(target < arr[rows][i]){
                rows--;
            }else if (target > arr[rows][i]){
                i++;
            }else {
                return true;
            }
        }
        return false;
    }
}
