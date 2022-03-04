package com.example.algorithm.niukewang;

public class PrintPostOrder {
    public void printPostOrder(Node node){
        if (node == null){
            return;
        }

        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(""+node.val);
    }
}
