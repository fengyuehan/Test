package com.example.algorithm.niukewang;

public class GetLeafNumber {
    /*
          打印叶节点的个数
     */
    public int getLeafNumber(Node node){
        if (node == null){
            return 0;
        }

        if (node.left == null && node.right == null){
            return 1;
        }

        return getLeafNumber(node.left) + getLeafNumber(node.right);
    }
}
