package com.example.datastructures;

public class IsTreeBalance {
    public boolean isTreeBalance(Node node){
        if (node == null){
            return true;
        }

        if (Math.abs(treeDepth(node.left) - treeDepth(node.right)) > 1){
            return false;
        }else {
            return isTreeBalance(node.left) && isTreeBalance(node.right);
        }
    }

    private int treeDepth(Node node){
        if (node == null){
            return 0;
        }
        return Math.max(treeDepth(node.left),treeDepth(node.right)) + 1;
    }
}
