package com.example.datastructures;

public class PrintInOrder {
    public void printInOrder(Node node){
        if (node == null){
            return;
        }
        printInOrder(node.left);
        System.out.print(""+ node.value);
        printInOrder(node.right);
    }
}
