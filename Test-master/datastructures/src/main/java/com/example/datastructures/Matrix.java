package com.example.datastructures;

public class Matrix {
    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请
     * 完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public boolean find(int[][] matrix,int number){
        if (matrix.length < 1 || matrix[0].length<1 || matrix == null){
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[1].length;
        int row = 0;
        int col = cols - 1;
        while (row >= 0 && row < rows && col >= 0 && col < cols){
            if (matrix[row][col] == number){
                return true;
            }else if (matrix[row][col] > number){
                col--;
            }else {
                row++;
            }
        }
        return false;
    }

}
