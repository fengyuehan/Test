package com.example.datastructures;

public class SwapTree {
    /*
        获取树的镜像
     */
    public void swapTree(Node node){
        if (node == null){
            return;
        }

        Node temp = node.left;
        node.left = node.right;
        node.right = temp;
        swapTree(node.left);
        swapTree(node.right);
    }
}
