package com.example.datastructures;

import java.util.LinkedList;
import java.util.List;

public class PrintTreeLevelOrder {
    /*
        分层打印
     */
    public void printFromTopToBottom(Node node){
        if (node == null || node.root == null){
            return;
        }

        Node root = node.root;
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(root);
        //对应于下一层的最后一个元素
        Node end = root;
        while (!queue.isEmpty()){
            Node node1 = queue.getFirst();
            if (node1.left != null){
                queue.offer(node1.left);
            }
            if (node1.right!= null){
                queue.offer(node1.right);
            }

            if (node1 == end){
                //换行
                System.out.print("\n");
                end = queue.getLast();
            }
        }
        queue.pop();
    }
}
