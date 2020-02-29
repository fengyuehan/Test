package com.example.datastructures;

public class GetTreeHeight {

    /*
        获取树的高度
     */
    public int getTreeHeight(Node node){
        if (node == null){
            return 0;
        }

        int left = getTreeHeight(node.left);
        int right = getTreeHeight(node.right);

        return left > right? left:right;
    }
}
