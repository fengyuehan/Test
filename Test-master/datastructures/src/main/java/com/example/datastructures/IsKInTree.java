package com.example.datastructures;

public class IsKInTree {
    /*
        判断是否在二叉树上
     */
    public boolean isKInTree(Node node,int k){
        if (node == null){
            return false;
        }

        if (node.value == k){
            return true;
        }

        boolean has;

        has = isKInTree(node.left,k);
        if (!has){
            has = isKInTree(node.right,k);
        }
        return has;
    }
}
