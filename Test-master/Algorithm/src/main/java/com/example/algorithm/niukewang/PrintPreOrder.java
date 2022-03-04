package com.example.algorithm.niukewang;

public class PrintPreOrder {
    public void printPreOrder(Node node){
        if (node == null){
            return;
        }
        System.out.print("" + node.val);
        printPreOrder(node.left);
        printPreOrder(node.right);
    }
}
