package com.example.datastructures;

public class PrintPreOrder {
    public void printPreOrder(Node node){
        if (node == null){
            return;
        }
        System.out.print("" + node.value);
        printPreOrder(node.left);
        printPreOrder(node.right);
    }
}
